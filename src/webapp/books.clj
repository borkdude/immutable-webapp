(ns webapp.books
  (:require [webapp.db :as db]
            [webapp.animals :as animals]))

(defn create!
  ([]
   (db/create! {:type :book}))
  ([m]
   (db/create! (assoc m :type :book))))

(defn read
  ([]
   (db/read :type :book))
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
    (create! {:title    "Clojure Programming"
              :authors  ["Chas Emerick" "Brian Carper" "Christophe Grand"]
              :released #inst "2012-03-01"
              :img      "0636920013754.gif"
              :animal   (:db/id (first (animals/read :name "Painted-snipe")))})

    (create! {:title    "ClojureScript Up and Running"
              :authors  ["Stuart Sierra" "Luke VanderHart"]
              :released #inst "2012-10-01"
              :img      "0636920025139.gif"
              :animal   (:db/id (first (animals/read :name "Yellow-backed duiker")))})

    (create! {:title    "Clojure Cookbook"
              :authors  ["Luke VanderHart" "Ryan Neufeld"]
              :released #inst "2014-03-01"
              :img      "0636920029786.gif"
              :animal   (:db/id (first (animals/read :name "Aardwolf")))})))
