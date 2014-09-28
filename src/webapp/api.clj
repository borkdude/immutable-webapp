(ns webapp.api
  (:require [webapp.html :as html]
            [webapp.animals :as animals]
            [webapp.books :as books]
            [liberator.core :refer (resource)]
            [compojure.core :refer (defroutes ANY)]
            [compojure.route :refer (resources not-found)]
            [ring.middleware.params :refer (wrap-params)]
            [ring.middleware.edn :refer (wrap-edn-params)]
            [ring.util.response :refer (redirect)]
            [clj-json.core :as json]))

(defn handle-exception
  [ctx]
  (let [e (:exception ctx)]
    (.printStackTrace e)
    {:status 500 :message (.getMessage e)}))

(defroutes routes
  
  (ANY "/animals"
       []
       (resource
        :available-media-types ["application/edn" "application/json" "text/html"]
        :allowed-methods [:get :post]
        :handle-ok (fn [ctx]
                     (let [found (animals/read)]
                       (condp = (-> ctx :representation :media-type)
                         "application/edn" found
                         "application/json" (json/generate-string found)
                         "text/html" (html/generate-string found))))
        :post! (fn [ctx] {::id (animals/create!)})
        :post-redirect? (fn [ctx] {:location (str "/animals/" (::id ctx))})
        :handle-exception handle-exception))

  (ANY "/animals/:id"
       {body :body {id :id} :params}
       (resource
        :available-media-types ["application/edn"]
        :allowed-methods [:get :put :delete]
        :handle-ok (fn [ctx] (animals/read (Integer/parseInt id)))
        :put! (fn [ctx]
                (animals/update! (Integer/parseInt id)
                                 (clojure.edn/read-string (slurp body))))
        :delete! (fn [ctx] (animals/delete! (Integer/parseInt id)))
        :handle-exception handle-exception))

  (ANY "/books"
       []
       (resource
        :available-media-types ["application/edn"]
        :allowed-methods [:get]
        :handle-ok (fn [ctx] (books/read))
        :handle-exception handle-exception))
  
  (ANY "/"
       []
       (redirect "/index.html"))

  (resources "/" {:root "public"})
  (resources "/" {:root "/META-INF/resources"})
  (not-found "404"))

(def handler
  (-> routes
      wrap-params
      wrap-edn-params))
