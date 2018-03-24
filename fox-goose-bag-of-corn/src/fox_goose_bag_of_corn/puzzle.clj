(ns fox-goose-bag-of-corn.puzzle
  (:require [clojure.set]))

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])


(defn all-on-right? [plan]
  (let [last-move (map set (last plan))]
    (= last-move [#{} #{:boat} #{:fox :goose :corn :you}])))


(defn what-to-take-while-going [left-bank-state]
  (cond
    (<= (count (clojure.set/difference left-bank-state #{:you})) 1) left-bank-state
    (contains? left-bank-state :goose) #{:goose :you}
    :otherwise #{:you (first (clojure.set/difference left-bank-state #{:you}))}
    )
  )


(defn what-to-take-while-leaving [right-bank-state]
  (cond
    (<= (count (clojure.set/difference right-bank-state #{:you})) 1) #{:you}
    (not (contains? right-bank-state :goose)) #{:you}
    :otherwise #{:you :goose}
    )
  )

(defn find-next-move [last-move forward?]
(let [left (first last-move)
      middle (second last-move)
      right (last last-move)
      middle-except-boat (clojure.set/difference middle #{:boat})]
  (cond
    (contains? middle :you ) (if forward? {:forward false  :move [left #{:boat} (clojure.set/union right middle-except-boat)]}
                                             {:forward true :move [(clojure.set/union left middle-except-boat) #{:boat} right]})
    (contains? left :you) (let [to-take (what-to-take-while-going left)]
                            {:forward true :move [(clojure.set/difference left to-take)
                                                  (clojure.set/union middle to-take)
                                                  right]})
    :otherwise (let [to-take (what-to-take-while-leaving right)]
                 {:forward false :move [left
                                        (clojure.set/union middle to-take)
                                        (clojure.set/difference right to-take)]})
    )
  ) )

;
;



(defn river-crossing-plan []
  ; (loop [out start-pos]
  ;  (if (all-on-right? out)
  ;  out
  ;  (recur (conj out (find-next-move out)))))

  start-pos)

