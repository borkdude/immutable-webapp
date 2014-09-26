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

(defroutes routes
  (ANY "/animals"
       []
       (resource
        :available-media-types ["application/edn" "application/json" "text/html"]
        :allowed-methods [:get]
        :handle-ok (fn [ctx]
                     (let [found (animals/read)]
                       (condp = (-> ctx :representation :media-type)
                         "application/edn" found
                         "application/json" (json/generate-string found)
                         "text/html" (html/generate-string found))))))

  (ANY "/books"
       []
       (resource
        :available-media-types ["application/edn" "application/json" "text/html"]
        :allowed-methods [:get]
        :handle-ok (fn [ctx]
                     (let [found (books/read)]
                       (condp = (-> ctx :representation :media-type)
                         "application/edn" found
                         "application/json" (json/generate-string found)
                         "text/html" (html/generate-string found))))))
  
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
