(ns mw2gitit.views.welcome
  (:require [mw2gitit.views.common :as common]
            [noir.content.getting-started])
  (:use [noir.core :only [defpage]]
        [hiccup.core :only [html]]))

(defpage "/welcome" []
         (common/layout
           [:p "Welcome to mw2gitit"]))
