(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set]))

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])


(defn all-on-right? [plan]
  (let [last-move (map set (last plan))]
    (= last-move [#{} #{:boat} #{:fox :goose :corn :you}])))

(defn find-next-move [last-move forward?]
(let [left (first last-move)
      middle (second last-move)
      right (last last-move)
      middle-except-boat (clojure.set/difference middle #{:boat})]
  (cond
    (contains? middle :you ) (if forward? {:forward false  :move [left #{:boat} (clojure.set/union right middle-except-boat)]}
                                             {:forward true :move [(clojure.set/union left middle-except-boat) #{:boat} right]}))
  ) )

;
;



(defn river-crossing-plan []
  ; (loop [out start-pos]
  ;  (if (all-on-right? out)
  ;  out
  ;  (recur (conj out (find-next-move out)))))

  start-pos)

