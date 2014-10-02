(ns webapp.books
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [sablono.core :as html :refer-macros [html]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]
    [clojure.string :as string]))

(defonce books-state (atom []))

(defn book [b]
      [:div.row
       [:div.col-sm-4
        [:img {:src (str "/img/" (:img b))}]]
       [:div.col-sm-8
        [:div.panel.panel-default
         [:div.panel-heading
          [:h3.panel-title (:title b)]]
         [:div.panel-body
          [:p "Authors: " (string/join ", " (:authors b))]
          [:p "Released: " (.toLocaleDateString (:released b))]
          [:p "Animal on cover: " (-> b :animal :name)]]]]])

(go (let [response
          (<! (http/get "/books"))
          data (:body response)]
      (reset! books-state (set data))))

(defn books []
  [:div.container
   (map (fn [b]
          ^{:key (str "book-row-" (:id b))}
          [book b])
        (sort-by :title @books-state))])


