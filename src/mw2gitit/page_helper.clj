(ns mw2gitit.page_helper
  (:require [clojure.xml :as xml])
  (:require [clojure.java.shell :as shell])
  (:import [org.eclipse.mylyn.wikitext.core.parser MarkupParser])
  (:import [org.eclipse.mylyn.wikitext.mediawiki.core MediaWikiLanguage]))

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
    (filter #(nil? (re-find #"Category:.+" (get-title %)))
            (filter #(= (:tag %) :page)
                    (:content (xml/parse xml-file))))))

(defn summon-pandoc
  "Shell out to pandoc."
  [html-text]
  (shell/sh "pandoc" "--to=markdown" "--from=html" :in html-text))

(defn page-to-markdown
  [page-rec]
  (let [markup-parser (MarkupParser. (new MediaWikiLanguage))
        html-text (.parseToHtml markup-parser (:text page-rec))
        md-text (:out (summon-pandoc html-text))]
    (Page. (:title page-rec)
                (:timestamp page-rec)
                (:author page-rec)
                md-text)))


(defn page-rec-to-gitit
  [page-rec out-dir]
  (let [page (format "format: markdown\n%s\n" (:text page-rec))
        file-name (format "%s/%s.page" out-dir (:title page-rec))]
    (spit file-name page)))

(defn store-page
  "Expects an XML page description, and writes this to a file."
  [page out-dir]
  (page-rec-to-gitit (page-to-markdown
                      (build-page page))
                     out-dir))

;;  LocalWords:  pandoc
