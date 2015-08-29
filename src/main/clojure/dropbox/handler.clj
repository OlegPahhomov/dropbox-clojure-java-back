(ns dropbox.handler
  (:require [compojure.core :refer :all]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.json :refer [wrap-json-body]]
            [ring.middleware.defaults :refer [wrap-defaults api-defaults]]
            [ring.middleware.cors :refer [wrap-cors]]
            ))

(defn json-type [body]
  {:status  200
   :headers {"Content-Type"                "application/json"}

   :body    (str body)})

(defroutes app-routes
           (GET "/users" [] (json-type "hello world"))
           (route/not-found "Not Found"))

(def app
  (-> (wrap-defaults app-routes api-defaults)
      (wrap-params)
      (wrap-cors
        :access-control-allow-origin [#"http://localhost:63342/*"]
        :access-control-allow-methods [:get :post]
        :access-control-allow-headers [:all])
      ))
