(defproject brave-bot "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :main brave-bot.core
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.9.0"]
                 [clj-http "3.8.0"]
                 [stylefruits/gniazdo "1.0.1"]
                 [cheshire "5.8.0"]
                 [tea-time "1.0.0-SNAPSHOT"]
                 [org.clojure/core.match "0.3.0-alpha5"]]
  :repositories {"local" ~(str (.toURI (java.io.File. "maven_repository")))})

