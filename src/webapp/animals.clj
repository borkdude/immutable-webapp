(ns webapp.animals
  (:require [webapp.db :as db]))

(defn create!
  ([]
     (db/create! {:type :animal}))
  ([m]
     (db/create! (assoc m :type :animal))))

(defn read
  ([]
     (db/read :type :animal))
  ([id]
     (db/read id))
  ([k v]
     (db/read k v)))

(defn update!
  [id m]
  (db/update! id m))

(defn delete!
  [id]
  (db/delete! id))

(defn init
  []
  (do
    (create! {:name "Painted-snipe"
              :class "Aves"
              :species "Rostratulidae"})
    (create! {:name "Aardwolf"
              :class "Mammalia"
              :species "Proteles cristata"})
    (create! {:name "Yellow-backed duiker"
              :class "Mammalia"
              :species "Cephalophus silvicultor"})))
