(ns webapp.main
  (:require [om.core :as om :include-macros true]
            [om.dom :as dom :include-macros true]
            [ankha.core :as ankha]))

(enable-console-print!)

(def app-state (atom {:text "Hello world!"}))

(om/root
  (fn [app owner]
    (reify om/IRender
      (render [_]
        (dom/div nil
                 (dom/h1 nil (:text app))
                 (dom/button #js {:className "btn btn-default"
                                  :onClick
                                  #(om/update! app :text "Clicked!")}
                             "Click to edit text")))))
  app-state
  {:target (. js/document (getElementById "app"))})

(om/root
 ankha/inspector
 app-state
 {:target (. js/document (getElementById "debug"))})
