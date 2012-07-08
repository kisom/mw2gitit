(ns mw2gitit.core
  (:gen-class)
  (:require [clojure.xml :as xml])
  (:require [clojure.tools.cli :as cli])
  (:require [mw2gitit.pages])
  (:require [mw2gitit.server :as server]))

(def ^:dynamic *output-dir* (ref (atom nil)))

(defn in?
  [coll val]
  (if (map? coll)
    (val coll)
    (not= -1 (.indexOf coll val))))

(defn- usage
  []
  (println "usage: lein run [opts] [files]")
  (println "  options include:")
  (println "    -s    enable web server interface")
  (System/exit 1))


;; (cli args
;;      ["-p" "--port" "Listen on this port" :parse-fn #(Integer. %)]
;;      ["-h" "--host" "The hostname" :default "localhost"]
;;      ["-v" "--[no-]verbose" :default true]
;;      ["-l" "--log-directory" :default "/some/path"])


(defn -main
  [& args]
  (let [[options args banner]
        (cli/cli args
                 ["-s" "--server" "Use the web server interface."]
                 [ "-o" "--output" "Specify the output directory."
                   :default "data/"])]
    (when (empty? args)
      (println "mw2gitit: convert mediawiki XML dumps to gitit")
      (println banner)
      (System/exit 1))
    (println "[+] okay")))