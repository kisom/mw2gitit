(ns mw2gitit.core
  (:require [clojure.xml :as xml])
  (:require [commandline.core :as cli])
  (:require [mw2gitit.pages :as pages])
  (:require [mw2gitit.server :as server]))

(defn -main
  [& args]
  (cli/with-commandline
    [args args]
    [[s server "fire up the web server interface"]]
    println args))