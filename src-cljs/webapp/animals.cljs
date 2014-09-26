(ns webapp.animals
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [sablono.core :as html :refer-macros [html]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]))

(defonce animals-state (atom #{}))
(defonce initial-form-values {:name ""
                              :species ""})
(defonce form-input-state (atom initial-form-values))

(defn add-animal [a]
  (swap! animals-state conj a)
  (reset! form-input-state initial-form-values))

(defn remove-animal [a]
  (swap! animals-state disj a))

(defn field-input-handler
  "Updates value in form-input-state atom map
  under key with value from onChange event"
  [key]
  (fn [e]
    (swap! form-input-state
           assoc key
           (.. e -target -value))))

(defn are-inputs-valid? []
  (and (seq (-> @form-input-state :name))
       (seq (-> @form-input-state :species))))

(defn animal-row [a]
      [:tr
       [:td (:name a)]
       [:td (:species a)
        [:button.btn.btn-primary.pull-right.btn-danger
         {:onClick #(remove-animal a)}
         "\u00D7"]]])

(go (let [response
          (<! (http/get "/animals"))
          data (:body response)]
      (println "this once")
      (reset! animals-state (set data))))

(defn animals []
      (fn
        []
        [:div
         [:table.table.table-striped
          [:thead
           [:tr
            [:th "Name"]
            [:th "Species"]]]
          [:tbody
           (map animal-row (sort-by :name @animals-state))
           [:tr
            [:td [:input {:type "text"
                          :value (-> @form-input-state :name)
                          :onChange (field-input-handler :name)}]]
            [:td [:input {:type "text"
                          :value (-> @form-input-state :species)
                          :onChange (field-input-handler :species)}]
             [:button.btn.btn-primary.pull-right
              {:disabled (not (are-inputs-valid?))
               :onClick #(add-animal @form-input-state)} "Add"]]]]]]))