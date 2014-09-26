(ns webapp.animals
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [sablono.core :as html :refer-macros [html]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]))

(defonce animals-state (atom #{}))

;; fire off go loop only once
(go (let [response
          (<! (http/get "/animals"))
          data (:body response)]
      (reset! animals-state (set data))))

(defn gen-id []
  (-> (js/Date.) .valueOf))

(defn add-animal! [a]
  (swap! animals-state conj (assoc a :id (gen-id))))

(defn remove-animal! [a]
  (swap! animals-state disj a))

(defn update-animal! [a]
  (swap! animals-state
         (fn [old-state]
           (conj
             (set (filter (fn [other]
                            (not= (:id other)
                                  (:id a)))
                          old-state))
             a))))

(defn field-input-handler
  "Updates value in atom map under key with value from onChange event"
  [atom key]
  (fn [e]
    (swap! atom
           assoc key
           (.. e -target -value))))

(defn editable-input [atom key]
  (if (:editing? @atom)
    [:input {:type     "text"
             :value    (get @atom key)
             :onChange (field-input-handler atom key)}]
    (get @atom key)))

(defn animal-row [idx a]
  (let [row-state (atom {:editing? false
                         :name     (:name a)
                         :species  (:species a)})
        current-animal (fn []
                         (assoc a
                           :name (:name @row-state)
                           :species (:species @row-state)))]
    (fn []
      [:tr
       [:td (editable-input row-state :name)]
       [:td (editable-input row-state :species)]
       [:td [:button.btn.btn-primary.pull-right
             {:onClick (fn []
                         (when (:editing? @row-state)
                           (update-animal! (current-animal)))
                         (swap! row-state update-in [:editing?] not))}
             (if (:editing? @row-state) "Save" "Edit")]]
       [:td [:button.btn.pull-right.btn-danger
             {:onClick #(remove-animal! (current-animal))}
             "\u00D7"]]])))

(defn animal-form []
  (let [initial-form-values {:name    ""
                             :species ""}
        form-input-state (atom initial-form-values)
        are-inputs-valid? (fn []
                            (and (seq (-> @form-input-state :name))
                                 (seq (-> @form-input-state :species))))]
    (fn []
      [:tr
       [:td [:input {:type     "text"
                     :value    (-> @form-input-state :name)
                     :onChange (field-input-handler form-input-state :name)}]]
       [:td [:input {:type     "text"
                     :value    (-> @form-input-state :species)
                     :onChange (field-input-handler form-input-state :species)}]]
       [:td [:button.btn.btn-primary.pull-right
             {:disabled (not (are-inputs-valid?))
              :onClick  (fn []
                          (add-animal! @form-input-state)
                          (reset! form-input-state initial-form-values))}
             "Add"]]])))

(defn animals []
  [:div
   [:table.table.table-striped
    [:thead
     [:tr
      [:th "Name"]
      [:th "Species"]
      [:th ""]
      [:th ""]]]
    [:tbody
     (map-indexed
       (fn [idx a]
         ^{:key (str "animal-row" (:id a))}
         [animal-row idx a])
       (sort-by :name @animals-state))
     [animal-form]]]])