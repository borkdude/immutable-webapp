(ns webapp.db)

(defn create
  [r m]
  (dosync
   (let [id (+ 1 (apply max (keys (deref r))))]
     (alter r assoc id (assoc m :id id)))))

(defn read
  ([r]
     (vals (deref r)))
  ([r id]
     (get (deref r) id)))

(defn update
  [r m]
  (dosync
   (when-let [id (:id m)]
     (when-let [found (get (deref r) id)]
       (let [merged (merge found m)]
         (alter r assoc id merged)
         merged)))))

(defn delete
  [r id]
  (if (get (deref r) id)
    (dosync
     (alter r dissoc id))))
