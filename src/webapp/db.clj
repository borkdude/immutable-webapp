(ns webapp.db
  (:require [datomic.api :as d]
            [clojure.java.io :refer (resource)]))

;; Datomic

(defonce connection (atom nil))

(defn conn
  []
  (if (nil? @connection)
    (throw (RuntimeException. "No database connection."))
    @connection))

(defn init
  []
  (let [uri "datomic:mem://webapp"
        schema (read-string (slurp (resource "schema.edn")))]
    (d/create-database uri)
    (reset! connection (d/connect uri))
    (d/transact (conn) schema)
    nil))

(defn db
  []
  (d/db (conn)))

;; db api

(defonce max-id (atom 0))

(defn next-id
  [] (swap! max-id inc))

(defn create!
  [m]
  (let [id (next-id)
        dbid (d/tempid :db.part/user)]
    @(d/transact (conn) (list (assoc m :db/id dbid :id id)))
    id))

(defn read
  ([id]
     (let [found (d/q '[:find ?e :in $ ?id :where [?e :id ?id]] (db) id)]
       (if (seq found)
         (d/entity (db) (ffirst found)))))
  ([k v]
     (let [found (d/q '[:find ?e :in $ ?k ?v :where [?e ?k ?v]] (db) k v)]
       (map (comp (partial d/entity (db)) first) found))))

(defn update!
  [id m]
  (if-let [found (read id)]
    (do @(d/transact (conn) (map (fn [k v] [:db/add (:db/id found) k v])
                                 (keys m) (vals m)))
        true)
    false))

(defn delete!
  [id]
  (if-let [found (read id)]
    (do @(d/transact (conn) [[:db.fn/retractEntity (:db/id found)]])
        true)
    false))

(defn expand
  ([e]
     (if (instance? datomic.query.EntityMap e)
       (let [m (into {} (d/touch e))]
         (expand m (keys m)))
       e))
  ([e ks]
     (if-not (empty? ks)
       (let [val (get e (first ks))]
         (cond
          (instance? datomic.query.EntityMap val)
          (expand (assoc e (first ks) (expand val)) (rest ks))
          (and (set? val) (instance? datomic.query.EntityMap (first val)))
          (expand (assoc e (first ks) (set (map expand val))) (rest ks))
          :else (expand e (rest ks))))
       e)))
