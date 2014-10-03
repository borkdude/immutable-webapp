(ns webapp.query-test
  (:use clojure.test)
  (:require [webapp.db :as db]
            [webapp.animals :as animals]
            [webapp.books :as books]
            [datomic.api :as d]))

(deftest find-all-mammal-entities
  (testing "Query by attribute"
    (if-let [query nil]
      (is (= 3 (count (d/q query (db/db)))))
      (is false "Please implement this query!"))))

(deftest find-the-horny-toad
  (testing "Query by attribute with param"
    (if-let [query nil]
      (let [result (d/q query (db/db) "Horny toad")
            animal (d/entity (db/db) (ffirst result))]
        (is (= "Phrynosoma cornutum" (:species animal) )))
      (is false "Please implement this query!"))))

(deftest find-the-book-titles-released-before-2007
  (testing "Query with predicates"
    (if-let [query nil]
      (is (= 4 (count (d/q query (db/db) #inst "2007-01-01"))))
      (is false "Please implement this query!"))))

(deftest find-the-book-title-with-connochaetes-gnou-on-the-cover
  (testing "Query with joins"
    (if-let [query nil]
      (is (= "Learning GNU Emacs"
             (ffirst (d/q query (db/db) "Connochaetes gnou"))))
      (is false "Please implement this query!"))))

(deftest calculate-the-sum-of-squares-of-book-pages
  (testing "Query with functions"
    (if-let [query nil]
      (let [sum-of-squares (Math/round (ffirst (d/q query (db/db))))]
        (is (= 149 sum-of-squares)))
      (is false "Please implement this query!"))))

(defn test-ns-hook
  []
  (db/init)
  (animals/init)
  (books/init)
  (find-all-mammal-entities)
  (find-the-horny-toad)
  (find-the-book-titles-released-before-2007)
  (find-the-book-title-with-connochaetes-gnou-on-the-cover)
  (calculate-the-sum-of-squares-of-book-pages)
  (db/close))
