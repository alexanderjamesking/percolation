(ns percolation.core
  (:require [reagent.core :as reagent]
            [clojure.core.async :as async :refer [chan <! >! timeout]])
  (:require-macros [cljs.core.async.macros :refer [go go-loop]]))

(def grid-size 10)

(def initial-state (zipmap (range (* grid-size grid-size)) (repeat :closed)))

(def id (reagent/atom initial-state))

(defn reset-state! []
  (reset! id initial-state))

(defn start! []
  (reset-state!)
  (go-loop [ids (shuffle (keys @id))]
    (<! (timeout 100))
    (if (empty? ids) ;; TODO: stop on percolate!
      (do :done
#_          (recur (shuffle (keys initial-state))))
      (do
        (swap! id assoc (first ids) :open)
        (recur (rest ids))))))

(defn view []
  (let [squares (sort (map (juxt first second) @id))
        sqs (loop [squares squares
                   offset-x 0
                   offset-y 0
                   output [:g]]
              (if (empty? squares)
                output
                (let [[index state] (first squares)
                      new-row? (zero? (mod index grid-size))
                      offset-x (if new-row? 0 offset-x)
                      offset-y (if new-row? (+ offset-y 32) offset-y)]
                  (recur (rest squares)
                         (+ offset-x 32)
                         offset-y
                         (conj output [:rect {:x offset-x
                                              :y offset-y
                                              :width 30
                                              :height 30
                                              :fill (if (= :closed state)
                                                      "black"
                                                      "cyan")}])))))]
    [:div
     [:button {:on-click start!} "Start!"]
     [:svg {:width 400 :height 400}
      sqs]]))

(reagent/render-component [view]
                          (js/document.getElementById "app"))
