(ns webapp.db)

(def max-id (atom 0))

(defn next-id
  [] (swap! max-id inc))

(defn create
  [r m]
  (dosync
   (let [id (next-id)]
     (alter r assoc id (assoc m :id id)))))

(defn read
  ([r]
     (vals (deref r)))
  ([r id]
     (get (deref r) id)))

(defn update
  [r id m]
  (dosync
   (when (contains? (deref r) id)
     (alter r update-in [id] merge m))))

(defn delete
  [r id]
  (if (get (deref r) id)
    (dosync
     (alter r dissoc id))))
