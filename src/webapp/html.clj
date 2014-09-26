(ns webapp.html
  (:require [hiccup.core :refer (html)]))

(defn domify
  [x]
  (cond
   (map? x) [:table (map (fn [k v] [:tr [:td (domify k)] [:td (domify v)]])
                         (keys x) (vals x))]
   (coll? x) [:div (map domify x)]
   (keyword? x) (name x)
   :else x))

(defn generate-string
  [x]
  (html (domify x)))
