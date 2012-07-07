(defproject mw2gitit "0.1.0-SNAPSHOT"
            :description "FIXME: write this!"
            :dependencies [[org.clojure/clojure "1.3.0"]
                           [commandline-clj "0.1.2"]
                           [org.eclipse.mylyn.wikitext/wikitext
                            "0.9.4.I20090220-1600-e3x"]
                           [org.eclipse.mylyn.wikitext/wikitext.mediawiki
                            "0.9.4.I20090220-1600-e3x"]
                           [org.eclipse.mylyn.wikitext/wikitext.textile
                            "0.9.4.I20090220-1600-e3x"]
                           [noir "1.2.1"]]
            :main mw2gitit.core)
