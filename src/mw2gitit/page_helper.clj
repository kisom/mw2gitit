(ns mw2gitit.page_helper
  (:require [clojure.xml :as xml])
  (:import [org.eclipse.mylyn.wikitext.core.parser MarkupParser])
  (:import [org.eclipse.mylyn.wikitext.mediawiki.core MediaWikiLanguage])
  (:import [org.eclipse.mylyn.wikitext.textile.core TextileLanguage]))

;;;; code for dealing with pages in the wiki

;;;; TODO: write a proper parser that will descend into the XML
;;;; madness and properly extract the tags we're looking for, instead
;;;; of the current horrifically hacky solutions.

(defrecord Page
    [title timestamp author text])

(defn- get-title
  "Pull the title from a page."
  [page]
  (first
   (:content
    (first
     (filter #(= (:tag %))
             (:content page))))))

(defn- get-timestamp
  "Pull the timestamp from a page."
  [page]
  (first
   (:content
    (first
     (filter #(= (:tag %) :timestamp)
             (:content
              (first
               (filter #(= (:tag %) :revision)
                       (:content page)))))))))

(defn- get-author
  "Pull the author from a page."
  [page]
  (first
   (:content
    (first
     (filter #(= (:tag %) :username)
             (:content
              (first
               (filter #(= (:tag %) :contributor)
                       (:content
                        (first
                         (filter #(= (:tag %) :revision)
                                 (:content page))))))))))))

(defn- get-content
  "Get the page content from the wiki."
  [page]
  (first
   (:content
    (first
     (filter #(= (:tag %) :text)
             (:content
              (first
               (filter #(= (:tag %) :revision)
                       (:content page)))))))))

(defn build-page
  [page]
  (Page. (get-title page)
         (get-timestamp page)
         (get-author page)
         (get-content page)))

(defn get-pages
  "Given an XML file, extract the entities."
  [xml-file]
  (let [xml-dump (:content (xml/parse xml-file))]
    (filter #(= (:tag %) :page)
            (:content (xml/parse xml-file)))))

;; (defn page-to-textile
;;   [page-rec]
;;   (doto (MarkupParser.)
;;     (. ))

;;   )
