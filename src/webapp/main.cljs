(ns webapp.main
  (:require [reagent.core :as reagent :refer [atom]]
            [sablono.core :as html :refer-macros [html]]))

(enable-console-print!)

(defn books []
  [:div "animals"])

(defn mountit []
  (reagent/render-component [books]
                            (js/document.getElementById "app")))

(mountit)
