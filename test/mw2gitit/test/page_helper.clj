(ns mw2gitit.test.page_helper
  (:use [mw2gitit.page_helper :as page-helper])
  (:use [clojure.test]))

(defonce test-page "data/test-page.xml")
(defonce valid-text
  "This is a test page. We are verifying it works. [[Main Page]]")

(deftest load-page
  "Ensure the library actually loads."
  (let [page (page-helper/get-pages test-page)]
    (is (= 1 (count page)))))

;; note timestamp is ignored here
(deftest page-integrity
  "Does the page load the proper information?"
  (let [pages (page-helper/get-pages test-page)
        page (page-helper/build-page (first pages))]
    (is (= (:author page) "Kyle"))
    (is (= (:title page) "Test-page"))
    (is (= (:text page) valid-text))))