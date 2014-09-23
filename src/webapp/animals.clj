(ns webapp.animals
  (:require [webapp.db :as db]))

(def animals
  (ref
   {1 {:id (db/next-id)
       :name "Painted-snipe"
       :species "Rostratulidae"
       :img "0636920013754.gif"}
    2 {:id (db/next-id)
       :name "Aardwolf"
       :species "Proteles cristata"
       :img "0636920029786.gif"}
    3 {:id (db/next-id)
       :name "Yellow-backed duiker"
       :species "Cephalophus silvicultor"
       :img "0636920025139.gif"}}))

(defn create
  [a]
  (db/create animals a))

(defn read
  ([]
     (db/read animals))
  ([id]
     (db/read animals id)))

(defn update
  [id a]
  (db/update animals id a))

(defn delete
  [id]
  (db/delete animals id))
