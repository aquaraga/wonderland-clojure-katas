(ns wonderland-number.finder)


(defn hasAllTheSameDigits? [n1 n2]
  (let [s1 (set (str n1))
        s2 (set (str n2))]
    (= s1 s2)))

(defn wonderland-number []
  (let [possible (range 100000 (quot 999999 6))
        all-multiples-fns (map #(partial * %) (range 2 7))
        all-multiples-of-num (fn [num] (map #(% num) all-multiples-fns))
        same-digits-for-num
          (fn [num] (every? #(hasAllTheSameDigits? % num) (all-multiples-of-num num)))]
    (first (filter #(same-digits-for-num %) possible)))
  )
