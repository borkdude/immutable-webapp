(ns webapp.animals)

(def animals
  (ref
   {1 {:id 1
       :name "Painted-snipe"
       :species "Rostratulidae"
       :img "0636920013754.gif"}
    2 {:id 2
       :name "Aardwolf"
       :species "Proteles cristata"
       :img "0636920029786.gif"}
    3 {:id 3
       :name "Yellow-backed duiker"
       :species "Cephalophus silvicultor"
       :img "0636920025139.gif"}}))

(defn create
  [a]
  (dosync
   (let [id (+ 1 (apply max (keys @animals)))]
     (alter animals assoc id (assoc a :id id)))))

(defn read
  ([]
     (dosync
      (vals @animals)))
  ([id]
     (get @animals id)))

(defn update
  [a]
  (when-let [id (:id a)]
    (when-let [found (get @animals id)]
      (dosync
       (let [merged (merge found a)]
         (alter animals update-in [id] merged)
         merged)))))

(defn delete
  [id]
  (if (get @animals id)
    (dosync
     (alter animals dissoc id))))
