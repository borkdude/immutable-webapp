(ns webapp.books
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]
    [clojure.string :as string]))

(defonce books-state (atom []))

(defn books []
  [:div.container])


