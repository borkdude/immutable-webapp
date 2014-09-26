(ns webapp.main
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require [reagent.core :as reagent :refer [atom]]
            [sablono.core :as html :refer-macros [html]]
            [cljs-http.client :as http]
            [cljs.core.async :refer (<!)]))

(enable-console-print!)

(def screen-state (atom 1))
(def books-state (atom nil))
(def animals-state (atom nil))


(defn book [b]
  [:div.row
   [:div.col-sm-4
    [:img {:src (str "/img/" (:img b))}]]
   [:div.col-sm-8
    [:p "Title: " (:title b)]
    [:p "Authors: " (pr-str (:authors b))]
    [:p "Released: " (.toLocaleDateString (:released b))]
    [:p "Animal on cover: " (-> b :animal :name)]]])

(defn animal-row [a]
  [:tr
   [:td (:name a)]
   [:td (:species a)]])

(defn animals []
  (go (let [response
            (<! (http/get "/animals"))
            data (:body response)]
        (reset! animals-state data)))
  (fn []
    [:table.table.table-striped
     [:thead
      [:tr
       [:th "Name"]
       [:th "Species"]]]
     [:tbody
      (map animal-row @animals-state)]]))

(defn books []
  (go (let [response
            (<! (http/get "/books"))
            data (:body response)]
        (reset! books-state data)))
  (fn [] [:div.container
          (map book @books-state)]))

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
