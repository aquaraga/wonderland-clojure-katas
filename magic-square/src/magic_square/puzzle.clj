(ns magic-square.puzzle (:require [clojure.math.combinatorics :as combo]))


(def values [1.0 1.5 2.0 2.5 3.0 3.5 4.0 4.5 5.0])


(defn sum-rows [m]
  (map #(reduce + %) m))

(defn sum-cols [m]
  [(reduce + (map first m))
   (reduce + (map second m))
   (reduce + (map last m))])

(defn sum-diagonals [m]
  [(+ (get-in m [0 0]) (get-in m [1 1]) (get-in m [2 2]))
   (+ (get-in m [2 0]) (get-in m [1 1]) (get-in m [0 2]))])


(defn convert-to-matrix [vals]
  (vec (map #(vec %) (partition 3 vals))))

(defn is-magic-square? [m]
  (and
    (= (set (sum-rows m))
           (set (sum-cols m))
           (set (sum-diagonals m)))

    (= 1
           (count (set (sum-rows m)))
           (count (set (sum-cols m)))
           (count (set (sum-diagonals m))))
    ))



(defn magic-square [values]
  (let [all-squares (map convert-to-matrix (combo/permutations values))]
    (first (filter is-magic-square? all-squares))
  )
)
