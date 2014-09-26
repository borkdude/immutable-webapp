(ns webapp.animals
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [sablono.core :as html :refer-macros [html]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]))

(defonce animals-state (atom nil))

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