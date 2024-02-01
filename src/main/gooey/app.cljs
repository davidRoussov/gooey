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

(defn catch-all [params]
  (let [path (:path params)]
    (let [maybe-url (subs path 1)]
      (js/console.log maybe-url)
      (fn []
        [:h1 "test"]
      )
    )
  )
)

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

(defn build-tree [data parent-id]
  (let [children (filter #(= (:parent_id %) parent-id) data)]
    (if (seq children)
      (map (fn [child]
             (assoc child :children (build-tree data (:id child)))) children)
      []
    )
  )
)

(defn chat-item [node]
  [:div
    [:div
      {:dangerouslySetInnerHTML {:__html (:content node)}}
    ]
    (when-let [children (:children node)]
      (map chat-item children)
    )
  ]
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
      (let [tree (build-tree (js->clj chat-json :keywordize-keys true) "")]
        (js/console.log "tree:" (clj->js tree))
        [:div {:class "container my-5"}
          [:div {:class "row"}
            [:div {:class "col-12"}
              [:h2 "Chat"]
              (for [item tree :let [index (.indexOf tree item)]]
                [:div {:class "card card-body mb-5"}
                  [chat-item item]
                ]
              )
            ]
          ]
        ]
      )
    )
    (catch :default e
      (println "Failed to parse JSON:" (.-message e))
      [:div {:class "alert alert-danger"} "An error occured while parsing chat data"]
    )
  )
)

(defonce match (r/atom nil))

(defn toggle-dark-mode [is-dark-mode]
  (cond
    is-dark-mode (->
      (.-documentElement js/document)
      (.setAttribute "data-bs-theme" "dark")
    ) 
    :else (->
      (.-documentElement js/document)
      (.setAttribute "data-bs-theme" "light")
    ) 
  )
)

(defn app []
  (let [is-dark-mode (r/atom false)]
    (fn []
      (toggle-dark-mode @is-dark-mode)
      [:div
        [:div {:class "container my-5"}
          [:div {:class "row"}
            [:div {:class "col-12"}
              [:div {:class "d-flex justify-content-end"}
                [:div {:class "form-check form-switch form-check-reverse"}
                  [:input {
                    :type "checkbox"
                    :class "form-check-input"
                    :checked @is-dark-mode
                    :on-change #(reset! is-dark-mode (-> % .-target .-checked))
                    :id "darkModeSwitch"
                  }]
                  [:label {:class "form-check-label" :for "darkModeSwitch"}
                    "Dark Mode"
                  ]
                ]
              ]
            ]
          ]
        ]
        (if @match
          (let [view (:view (:data @match))]
            [view @match]))
      ]
    )
  )
)

(def routes
  [
    ["/"        {:conflicting true :name ::home :view home-page}]
    ["/chat"    {:conflicting true :name ::chat :view chat-page}]
    ["*"        {:conflicting true :name ::catch-all :view catch-all}]
  ]
)

(defn init! []
  (rfe/start!
    (rf/router routes {:data {:coercion rss/coercion}})
    (fn [m] (reset! match m))
    {:use-fragment false})
  (r/render [app] (.getElementById js/document "root")))

(init!)
