(ns webapp.main
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [webapp.animals :refer (animals)]
    [webapp.books :refer (books)]
    [reagent.core :as reagent :refer [atom]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]))

(enable-console-print!)

(defonce screen-state (atom 1))

(defn nav-pill [idx title]
  [:li {:class (if (= idx @screen-state)
                 "active")
        :onClick #(reset! screen-state idx)}
   [:a {:href "#"} title]])

(defn screen []
  [:div.container
   [:div.row
    [:ul.nav.nav-pills
     [nav-pill 1 "Animals"]
     [nav-pill 2 "Books"]]]
   [:div.row
    (case @screen-state
      1 [animals]
      2 [books]
      :else "Error")]])

(defn mountit []
  (reagent/render-component [screen]
                            (js/document.getElementById "app")))

(mountit)
