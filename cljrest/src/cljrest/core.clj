(ns cljrest.core
  (:gen-class)
   (:require [liberator.core :refer [resource defresource]]
            [ring.middleware.params :refer [wrap-params]]
            [compojure.core :refer [defroutes ANY]]))


(defresource parameter [txt]
  :available-media-types ["text/html"]
  :handle-ok (fn [_] (format "The text %s" txt)))

(defroutes app
  (ANY "/bar/:txt" [txt] (parameter txt)))

(def handler
  (-> app
      wrap-params))
