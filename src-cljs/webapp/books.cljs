(ns webapp.books
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [sablono.core :as html :refer-macros [html]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]
    [clojure.string :as string]))

(defonce books-state (atom []))

(go (let [response
          (<! (http/get "/books"))
          data (:body response)]
      (reset! books-state (set data))))

(defn books []
  [:div.container])


