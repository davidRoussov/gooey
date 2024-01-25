(ns gooey.app
  (:require [reagent.core :as reagent]
            [cljsjs.react]))

(defn app []
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
          [:textarea {:class "form-control" :rows "20"}

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

(defn mount-root []
  (reagent/render [app]
                  (.getElementById js/document "root")))

(defn init []
  (mount-root))
