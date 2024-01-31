(ns gooey.app
  (:require [reagent.core :as r]
            [reitit.frontend :as rf]
            [reitit.frontend.easy :as rfe]
            [reitit.coercion.spec :as rss]
            [spec-tools.data-spec :as ds]
            [cljs.js :as js]
  )
)

(defonce parser-output (r/atom ""))

(defn home-page []
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
          [:button {:type "button" :class "btn btn-primary btn-lg" :on-click #(rfe/push-state ::chat)}
            "Chat"
          ]
        ]
      ]
    ]
  )
)

(defn chat-page []
  (try
    (let [chat-json (js/JSON.parse @parser-output)]
      (js/console.log "Parsed JSON:" chat-json)
      (doseq [item chat-json]
        (let [item-keys (js-keys item)]
          (js/console.log "Keys for item: " item-keys)
        )
      )
      [:div
        [:h2 "Chat"]
        (for [item chat-json :let [index (.indexOf chat-json item)]]
          (doall
            (println "rendering item: " item)
            [:div {:key index :class "item"}
              [:h1 index]
              [:h3 (str "ID: " (.-id item))]
              [:p (str "content " (.-content item))]
            ]
          )
        )
      ]
    )
    (catch :default e
      (println "Failed to parse JSON:" (.-message e))
      [:div {:class "alert alert-danger"} "An error occured while parsing chat data"]
    )
  )
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
