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
           (java.io ByteArrayInputStream)
           (files.validator FileValidator)))

(defn response-json-type [body]
  {:status  200
   :headers {"Content-Type" "application/json"}
   :body    (str (json/write-str body))})

(defn response-img-type [image]
  {:status  200
   :headers {"Content-Type" "image/jpeg"}
   :body    (new ByteArrayInputStream image)})

(defn invalid-id? [id]
  (try
    (Long/valueOf id) false
    (catch Exception e true)))

(defn get-files [files]
  (get (stringify-keys files) "file"))

(defn invalid-files? [files]
  (try
    (FileValidator/invalidFiles (get-files files))
    (catch Exception e
      true)))

(defroutes app-routes
           (GET "/files" [] (-> (FileReader/getPictures)
                                response-json-type))
           (GET "/picture/:id" {params :params}
             (if (invalid-id? (params :id))
               (response-json-type "image does not exist")
               (-> (FileReader/getPicture (params :id))
                   response-img-type)))
           (wrap-multipart-params
             (POST "/add" {files :multipart-params}
               (if (invalid-files? files)
                 (response-json-type "failure")
                 (do (FileCrud/saveFiles (get-files files))
                     (response-json-type "success")))))
           (POST "/remove/:id" {params :params}
             (if (invalid-id? (params :id))
               (response-json-type "image does not exist")
               (do (FileCrud/deleteOneFile (params :id))
                   (response-json-type "success"))))
           (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-params)
      (wrap-cors
        :access-control-allow-origin [#"http://localhost:63342/*"]
        :access-control-allow-methods [:get :post]
        :access-control-allow-headers [:all])))
