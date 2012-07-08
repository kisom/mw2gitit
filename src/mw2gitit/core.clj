(ns mw2gitit.core
  (:require [clojure.xml :as xml])
  (:require [clojure.tools.cli :as cli])
  (:use [mw2gitit.page-helper])
  (:use [qmw2gitit.server])
  (:gen-class))

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

(defn -main
  [& args]
  (let [[options args banner]
        (cli/cli args
                 ["-s" "--server" "Use the web server interface." :flag true]
                 [ "-o" "--output" "Specify the output directory."
                   :default "data/"])]
    (when (empty? args)
      (println "mw2gitit: convert mediawiki XML dumps to gitit")
      (println banner)
      (System/exit 1))
    (println options)
    (if (in? options :server) (future mw2gitit.server/start))
    (if (in? options :output) nil)
    ;; (doseq [file args]
    ;;   (println "[+] loaded" (count (page-helper/get-pages file))
    ;;            "pages from" file))
    (println "[+] okay")))