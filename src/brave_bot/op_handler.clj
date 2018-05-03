(ns brave-bot.op-handler
  (:require [clojure.core.match :refer [match]]
            [cheshire.core :refer [parse-string encode]]
            [tea-time.core :as tt]
            [gniazdo.core :as ws]
            [clojure.pprint :refer [pprint]]))

(tt/start!)
(def token (.trim (slurp "token.txt")))

(defn safe-println [& more]
  (.write *out* (str (clojure.string/join " " more) "\n")))

(defonce heartbeater (atom nil))
(defonce s (atom nil))

(defn dispatch
  [bot event]
  (print "Dispatching" event))

(defn heartbeat
  [bot event]
  (print "Heartbeat" event))

(defn identify
  [bot]
  (println "Do the handshake")
  (ws/send-msg bot (encode {"op" 2 "d" {"token" token
                                        "properties" {"$os" "linux"
                                                      "$browser" "disco"
                                                      "$device" "disco"}}})))

(defn reconnect
  [bot event]
  )

(defn invalid-session
  [bot event]
  (print "This session is invalid" event))

(defn hello
  [bot event]
  (println "Greetings" event)
  (reset! heartbeater (tt/every! (/ (:heartbeat_interval event) 1000.0) 1
                                 (bound-fn []
                                   (safe-println "Sending a heartbeat!")
                                   (ws/send-msg bot (encode {"op" 1
                                                             "d" @s})))))
  (tt/after! 5 (bound-fn [] (identify bot))))

(defn heartbeat-ack
  [bot event]
  (print "Heartbeat was received" event))




(def op-map {0  dispatch
             1  heartbeat
             2  identify
             3  'status-update
             4  'voice-state-update
             5  'voice-server-ping
             6  'resume
             7  'reconnect
             8  'request-guild-members
             9  invalid-session
             10 hello
             11 heartbeat-ack})


(defn handler
  "ws message handler, dispatches to other methods based on opcode"
  [bot event]
  (pprint event)
  (reset! s (if-let [seq-num (:s event)]
              seq-num
              @s))
  ((op-map (:op event)) @bot (:d event)))

