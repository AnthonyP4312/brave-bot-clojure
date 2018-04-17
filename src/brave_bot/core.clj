(ns brave-bot.core
  (:gen-class)
  (:require [clj-http.client :as http]
            [gniazdo.core :as ws]
            [cheshire.core :as JSON :refer [parse-string]]))

(defn url
  [& routes]
  (apply str (interpose "/" (cons "https://discordapp.com/api" (flatten routes)))))

(url "boiii")

(def ws-atom (atom ""))
(defn ws-url
  "Return a new wss url from the discord api"
  ([] (ws-url false))
  ([reset]
   (cond
     (= @ws-atom "") (reset! ws-atom (:url (parse-string (:body (http/get (url "gateway"))) true)))
     (true? reset)   (reset! ws-atom (:url (parse-string (:body (http/get (url "gateway"))) true)))
     :else           @ws-atom)))

(ws-url)

(defn -main
  "Initializes the bot and caches websocket url"
  []
  (println "Hello, World!"))

