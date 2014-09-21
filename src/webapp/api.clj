(ns webapp.api
  (:require [webapp.html :as html]
            [liberator.core :refer (resource)]
            [compojure.core :refer (defroutes ANY)]
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
                     (let [animals {:animals [{:name "Foo"}]}]
                       (condp = (-> ctx :representation :media-type)
                         "application/edn" animals
                         "application/json" (json/generate-string animals)
                         "text/html" (html/generate-string animals))))))
  (ANY "/"
       []
       (redirect "/animals")))

(def handler
  (-> routes
      wrap-params
      wrap-edn-params))
