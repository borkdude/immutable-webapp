(ns webapp.db)

(defonce max-id (atom 0))

(defonce records (atom {}))

(defn next-id
  [] (swap! max-id inc))

(defn create!
  [m]
  (let [id (next-id)]
    (swap! records assoc id (assoc m :id id))
    id))

(defn read
  ([id]
     (get (deref records) id))
  ([k v]
     (filter #(= (get % k) v) (vals (deref records)))))

(defn update!
  [id m]
  (when (contains? (deref records) id)
    (swap! records update-in [id] merge m)
    nil))

(defn delete!
  [id]
  (if (contains? (deref records) id)
    (swap! records dissoc id)
    nil))
