(ns mw2gitit.server
  (:require [noir.server :as server]))

(server/load-views "src/mw2gitit/views/")

(defn start [& m]
  (println "[+] starting server")
  (let [mode (keyword (or (first m) :dev))
        port (Integer. (get (System/getenv) "PORT" "8080"))]
    (server/start port {:mode mode
                        :ns 'mw2gitit})))
