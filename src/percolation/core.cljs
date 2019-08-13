(ns percolation.core
  (:require [reagent.core :as reagent]))

(def id (reagent/atom [1 2 3 4 5 6 7 8 9]))

(defn view []
  [:div
   [:h1 "Hello world!"]

   [:p (str @id)]


   [:svg {:width 400 :height 400}

    [:rect {:x 0
            :y 0
            :width 500
            :height 500
            :fill "black"}]

    [:rect {:x 5
            :y 5
            :width 30
            :height 30
            :fill "#3498db"}]

    [:rect {:x 37
            :y 5
            :width 30
            :height 30
            :fill "#3498db"}]]

   ])

(reagent/render-component [view]
                          (js/document.getElementById "app"))
