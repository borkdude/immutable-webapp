(ns webapp.db)

(defonce max-id (atom 0))

(defn next-id
  [] (swap! max-id inc))

(defn create
  [a m]
  (let [id (next-id)]
    (swap! a assoc id (assoc m :id id))))

(defn read
  ([a]
     (vals (deref a)))
  ([a id]
     (get (deref a) id)))

(defn update
  [a id m]
  (when (contains? (deref a) id)
    (swap! a update-in [id] merge m)))

(defn delete
  [a id]
  (if (get (deref a) id)
    (swap! a dissoc id)))
