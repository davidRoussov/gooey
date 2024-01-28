(ns gooey.app
  (:require [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [spec-tools.data-spec :as ds]
  )
)

(defn home-page []
  (let [parser-output (r/atom "")]
    (fn []
      [:div {:class "container my-5"}
        [:div {:class "row"}
          [:div {:class "col-12"}
            [:h1 {:class "display-1 fw-bolder"} "Gooey"]
          ]
        ]
        [:div {:class "row"}
          [:div {:class "col-12"}
            [:div {:class "mt-5"}
              [:h2 {:id "document" :class "display-4 fw-bolder"} 
                "Document"
              ]
            ]
          ]
        ]
        [:div {:class "row"}
          [:div {:class "col-12"}
            [:div {:class "mt-5"}
              [:h2 {:id "document" :class "display-4 fw-bolder"} 
                "Parser"
              ]
            ]
          ]
        ]
        [:div {:class "row"}
          [:div {:class "col-12"}
            [:div {:class "mt-5"}
              [:h2 {:id "document" :class "display-4 fw-bolder"} 
                "Output"
              ]
              [:textarea 
                {
                  :class "form-control"
                  :rows "20"
                  :value @parser-output
                  :on-change #(reset! parser-output (-> % .-target .-value))
                }
              ]
            ]
          ]
        ]
        [:div {:class "row"}
          [:div {:class "col-12"}
            [:div {:class "mt-5"}
              [:h2 {:id "document" :class "display-4 fw-bolder"} 
                "Interface"
              ]
            ]
          ]
        ]
      ]
    )
  )
)

(defn chat-page []
  [:div
    [:h2 "Chat"]
  ]
)

(defonce match (r/atom nil))

(defn app []
  [:div
    (if @match
      (let [view (:view (:data @match))]
        [view @match]))
  ]
)

(def routes
  [
   ["/" {:name ::home :view home-page}]
   ["/chat" {:name ::chat :view chat-page}]
  ]
)

(defn init! []
  (rfe/start!
    (rf/router routes {:data {:coercion rss/coercion}})
    (fn [m] (reset! match m))
    {:use-fragment false})
  (r/render [app] (.getElementById js/document "root")))

(init!)
