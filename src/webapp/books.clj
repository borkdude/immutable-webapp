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
              :animal   (:db/id
                         (first (animals/read :name "Yellow-backed duiker")))})

    (create! {:title    "Clojure Cookbook"
              :authors  ["Luke VanderHart" "Ryan Neufeld"]
              :released #inst "2014-03-01"
              :img      "0636920029786.gif"
              :animal   (:db/id (first (animals/read :name "Aardwolf")))})

    (create! {:title    "Learning GNU Emacs"
              :authors  ["Debra Cameron" "James Elliott" "Marc Loy"
                         "Eric S. Raymond" "Bill Rosenblatt"]
              :released #inst "2004-12-01"
              :img      "9780596006488.gif"
              :animal   (:db/id (first (animals/read :name "Gnu")))})

    (create! {:title    "Graph Databases"
              :authors  ["Ian Robinson" "Jim Webber" "Emil Eifrem"]
              :released #inst "2013-06-20"
              :img      "0636920028246.gif"
              :animal   (:db/id (first (animals/read :name "Curled octopus")))})

    (create! {:title    "Ant: The Definitive Guide"
              :authors  ["Steve Holzner"]
              :released #inst "2005-04-01"
              :img      "9780596006099.gif"
              :animal   (:db/id (first (animals/read :name "Horny toad")))})

    (create! {:title    "Oracle RMAN Pocket Reference"
              :authors  ["Darl Kuhn" "Scott Schulze"]
              :released #inst "2001-11-01"
              :img      "9780596002336.gif"
              :animal   (:db/id (first (animals/read :name "Dung beetle")))})

    (create! {:title    "CSS: The Definitive Guide"
              :authors  ["Eric A. Meyer"]
              :released #inst "2006-11-01"
              :img      "9780596527334.gif"
              :animal   (:db/id (first (animals/read :name "Atlantic salmon")))})

    ))
