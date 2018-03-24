(ns fox-goose-bag-of-corn.puzzle)

(def start-pos [[[:fox :goose :corn :you] [:boat] []]])


(defn all-on-right? [plan]
  (let [last-move (map set (last plan))]
    (= last-move [#{} #{:boat} #{:fox :goose :corn :you}])))
;(defn find-next-move [plan]
;(let [last-move (map set (last plan))]
;  ) )

;
;



(defn river-crossing-plan []
  ; (loop [out start-pos]
  ;  (if (all-on-right? out)
  ;  out
  ;  (recur (conj out (find-next-move out)))))

  start-pos)

