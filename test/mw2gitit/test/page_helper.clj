(ns mw2gitit.test.page_helper
  (:use [mw2gitit.page_helper :as page-helper])
  (:use [clojure.test]))

(def test-page "data/test-page.xml")

(deftest load-page
  "Ensure the library actually loads."
  (let [page (page-helper/get-pages test-page)]
    (is (= 1 (count page)))))