(ns dropbox.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.multipart-params :refer [wrap-multipart-params]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.cors :refer [wrap-cors]]
            [clojure.data.json :as json]
            [clojure.walk :refer [stringify-keys]]
            )
  (:import (files.crud FileReader FileCrud)
           (java.io ByteArrayInputStream)))

(defn response-json-type [body]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (str body)})

(defn response-img-type [image]
  {:status  200
   :headers {"Content-Type" "image/jpeg"}
   :body    (new ByteArrayInputStream image)})


(defroutes app-routes
           (GET "/files" [] (-> (FileReader/getPictures)
                                json/write-str
                                response-json-type))
           (GET "/picture/:id" {params :params} (response-img-type (FileReader/getPicture (params :id))))
           (wrap-multipart-params
             (POST "/add" {files :multipart-params} (do
                                                      (FileCrud/saveFiles (get (stringify-keys files) "file"))
                                                      (response-json-type (json/write-str "success")))))
           (POST "/remove/:id" {params :params} (do (FileCrud/deleteOneFile (params :id))
                                                    (response-json-type (json/write-str "success"))))
           (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-params)
      (wrap-cors
        :access-control-allow-origin [#"http://localhost:63342/*"]
        :access-control-allow-methods [:get :post]
        :access-control-allow-headers [:all])
      ))
