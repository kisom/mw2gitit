(ns mw2gitit.test.page-helper
  (:use [mw2gitit.page-helper])
  (:use [clojure.test]))

(def test-page "data/test-page.xml")

(deftest load-page
  "Ensure the library actually loads."
  (let [page (get-pages test-page)])
  (is (= 1 (count page))))