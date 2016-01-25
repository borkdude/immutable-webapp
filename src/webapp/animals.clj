(ns webapp.animals
  (:refer-clojure :exclude [read])
  (:require [webapp.db :as db]
            [schema.core :as s]))

(def Animal
  {:name s/Str
   :species s/Str
   (s/optional-key :class) s/Keyword})

(defn create!
  ([]
   (db/create! {:type :animal}))
  ([m]
   (s/validate Animal m)
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

(create! {:name "Painted-snipe"
          :species "Rostratulidae"})

(create! {:name "Aardwolf"
          :species "Proteles cristata"})

(create! {:name "Yellow-backed duiker"
          :species "Cephalophus silvicultor"})
