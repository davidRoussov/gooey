(ns gooey.app
  (:require [reagent.core :as reagent]
            [cljsjs.react]))

(defn hello-world []
  [:div
   [:h1 "Hello, World!"]])

(defn mount-root []
  (reagent/render [hello-world]
                  (.getElementById js/document "root")))

(defn init []
  (mount-root))
