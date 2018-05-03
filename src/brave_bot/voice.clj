(ns brave-bot.voice
  (:require [brave-bot.core :refer [ws-url]]
            [gniazdo.core :as ws]
            [clojure.string :as str]
            [cheshire.core :refer [encode parse-string]]))

(def conn (atom nil))


(defn get-server-info
  [bot]
  (ws/send-msg bot {}))

(defn handler
  [bot event]
  (println "got event")
  (clojure.pprint/pprint event))

(defn connect
  []
  (let [url (str/replace (ws-url) #"6.*" "3")]
    (reset! conn (ws/connect url
                   :on-connect #(prn "Connected" %)
                   :on-receive #(handler conn (parse-string % true))
                   :on-error #(prn "Error on Websocket" %)
                   :on-close #(prn %1 %2)))))

