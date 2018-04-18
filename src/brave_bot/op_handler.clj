(ns brave-bot.op-handler
  (:require [clojure.core.match :refer [match]]
            [brave-bot.op :as op]))

(defn handler
  "ws message handler, dispatches to other methods based on opcode"
  [event]
  (match (:op event)
         ))
