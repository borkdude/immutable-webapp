(ns webapp.books
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [sablono.core :as html :refer-macros [html]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]))

(defonce books-state (atom nil))

(defn book [b]
      [:div.row
       [:div.col-sm-4
        [:img {:src (str "/img/" (:img b))}]]
       [:div.col-sm-8
        [:p "Title: " (:title b)]
        [:p "Authors: " (pr-str (:authors b))]
        [:p "Released: " (.toLocaleDateString (:released b))]
        [:p "Animal on cover: " (-> b :animal :name)]]])

(defn books []
      (go (let [response
                (<! (http/get "/books"))
                data (:body response)]
               (reset! books-state data)))
      (fn [] [:div.container
              (map book @books-state)]))

