(ns webapp.animals
  (:require-macros [cljs.core.async.macros :refer (go)])
  (:require
    [reagent.core :as reagent :refer [atom]]
    [cljs-http.client :as http]
    [cljs.core.async :refer (<!)]))

(defonce animals-state (atom #{}))

;; fire off go loop only once
(go (let [response
          (<! (http/get "/animals"))
          data (:body response)]
      (reset! animals-state (set data))))

;;; crud operations
(defn add-animal! [a]
  (go (let [response
            (<! (http/post "/animals" {:edn-params
                                        a}))]
        (swap! animals-state conj (:body response)))))

(defn remove-animal! [a]
  (go (let [response
            (<! (http/delete (str "/animals/"
                                  (:id a))))]
        (if (= (:status response)
                 200)
          (swap! animals-state disj a)))))

(defn update-animal! [a]
  (go (let [response
            (<! (http/put (str "/animals/" (:id a))
                          {:edn-params a}))
            updated-animal (:body response)]
        (swap! animals-state
               (fn [old-state]
                 (conj
                   (set (filter (fn [other]
                                  (not= (:id other)
                                        (:id a)))
                                old-state))
                   updated-animal))))))
;;; end crud operations

(defn field-input-handler
  "Returns a handler that updates value in atom map,
  under key, with value from onChange event"
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

(defn animal-row [a]
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
  (let [initial-form-values {:name     ""
                             :species  ""
                             :editing? true}
        form-input-state (atom initial-form-values)
        are-inputs-valid? (fn []
                            (and (seq (-> @form-input-state :name))
                                 (seq (-> @form-input-state :species))))]
    (fn []
      [:tr
       [:td (editable-input form-input-state :name)]
       [:td (editable-input form-input-state :species)]
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
     (map (fn [a]
            ^{:key (str "animal-row-" (:id a))}
            [animal-row a])
          (sort-by :name @animals-state))
     [animal-form]]]])
