(ns brave-bot.core
  (:gen-class)
  (:require [clj-http.client :as http]
            [gniazdo.core :as ws]
            [cheshire.core :as JSON :refer [parse-string]]))

(declare ws-handler)
(defonce bot (atom {}))

(defn url
  [& routes]
  (apply str (interpose "/" (cons "https://discordapp.com/api" (flatten routes)))))

(url "boiii")

(def ws-atom (atom ""))
(defn ws-url
  "Return a new wss url from the discord api"
  ([] (ws-url false))
  ([reset]
   (str (cond
          (= @ws-atom "") (reset! ws-atom (:url (parse-string (:body (http/get (url "gateway"))) true)))
          (true? reset)   (reset! ws-atom (:url (parse-string (:body (http/get (url "gateway"))) true)))
          :else           @ws-atom)
        "/?v=6&encoding=json")))

(ws-url)

(defn bot-connect
  []
  (ws/connect (ws-url)
    :on-connect #(prn "Connected" %)
    :on-receive #(ws-handler (parse-string % true))
    :on-error #(prn "Error on Websocket" %)))

(defn ws-handler
  [op]
  (println "Received an op" op))

(defn -main
  "Initializes the bot and caches websocket url"
  []
  (println "Hello, World!"))

