(ns fox-goose-bag-of-corn.puzzle-test
  (:require [clojure.test :refer :all]
            [fox-goose-bag-of-corn.puzzle :refer :all]
            [clojure.set]))

(deftest test-what-to-take-while-going
  (testing "if bank contains only one thing, take it"
    (is (= #{:goose :you} (what-to-take-while-going #{:goose :you}))))
  (testing "you can take yourself"
    (is (= #{:you} (what-to-take-while-going #{:you}))))
  (testing "leave behind something that doesn't eat the other thing"
    (is (= #{:goose :you} (what-to-take-while-going #{:goose :corn :fox :you}))))
  (testing "leave behind goose if only fox and goose is on left bank"
    (is (= #{:fox :you} (what-to-take-while-going #{:goose :fox :you}))))
  (testing "leave behind goose if only corn and goose is on left bank"
    (is (= #{:corn :you} (what-to-take-while-going #{:goose :corn :you}))))
  (testing "take any one if neither eats the other"
    (is (or (= #{:fox :you} (what-to-take-while-going #{:corn :fox :you}))
            (= #{:corn :you} (what-to-take-while-going #{:corn :fox :you})))))

  )


(deftest test-what-to-take-while-leaving
  (testing "if bank contains only one thing, leave it there"
    (is (= #{:you} (what-to-take-while-leaving #{:goose :you}))))
  (testing "you can take yourself"
    (is (= #{:you} (what-to-take-while-leaving #{:you}))))
  (testing "you should leave everything if nothing eats the other"
    (is (= #{:you} (what-to-take-while-leaving #{:corn :fox :you}))))
  (testing "take goose if it will eat or be eaten"
    (is (and (= #{:goose :you} (what-to-take-while-leaving #{:corn :goose :you}))
            (= #{:goose :you} (what-to-take-while-leaving #{:goose :fox :you})))))

  )


(deftest test-all-on-right
  (testing "you end with the fox, goose and corn on right side of the river"
    (is (all-on-right? [#{} #{:boat} #{:you :fox :goose :corn}])))
  (testing "you don't end up with any less than 4 items (including you) on the right side"
    (is (not (all-on-right? [#{} #{:boat :you} #{:fox :goose :corn}]))))
  )

(deftest test-next-move
  (testing "when you are moving forward in the boat, you get down on the right side"
    (is (= {:forward false :move [#{} #{:boat}  #{:fox :corn :you :goose}]}
             (find-next-move [#{} #{:boat :goose :you}  #{:fox :corn}] true))))
  (testing "when you are moving backward in the boat, you get down on the left side"
    (is (= {:forward true :move [#{:corn :you :goose} #{:boat}  #{:fox}]}
           (find-next-move [#{:corn} #{:boat :goose :you}  #{:fox}] false))))
  (testing "you must take something with you on your way forward, so that left doesn't get messed up"
    (is (= {:forward true :move [#{:corn :fox} #{:boat :you :goose}  #{}]}
           (find-next-move [#{:corn :you :fox :goose} #{:boat}  #{}] true))))
  (testing "you must leave the right bank, so that right doesn't get messed up"
    (is (= {:forward false :move [#{:corn} #{:boat :you :goose}  #{:fox}]}
           (find-next-move [#{:corn} #{:boat} #{:fox :you :goose}] false))))
  )


(defn validate-move [step1 step2]
  (testing "only you and another thing can move"
    (let [diff1 (clojure.set/difference step1 step2)
          diff2 (clojure.set/difference step2 step1)
          diffs (concat diff1 diff2)
          diff-num (count diffs)]
      (is (> 3 diff-num))
      (when (pos? diff-num)
        (is (contains? (set diffs) :you)))
      step2)))

(deftest test-river-crossing-plan
  (let [crossing-plan (map (partial map set) (river-crossing-plan))]
    (testing "you begin with the fox, goose and corn on one side of the river"
      (is (= [#{:you :fox :goose :corn} #{:boat} #{}]
             (first crossing-plan))))
    (testing "you end with the fox, goose and corn on one side of the river"
      (is (= [#{} #{:boat} #{:you :fox :goose :corn}]
             (last crossing-plan))))
    (testing "things are safe"
      (let [left-bank (map first crossing-plan)
            right-bank (map last crossing-plan)]
        (testing "the fox and the goose should never be left alone together"
          (is (empty?
               (filter #(= % #{:fox :goose}) (concat left-bank right-bank)))))
        (testing "the goose and the corn should never be left alone together"
          (is (empty?
               (filter #(= % #{:goose :corn}) (concat left-bank right-bank)))))))
    (testing "The boat can carry only you plus one other"
      (let [boat-positions (map second crossing-plan)]
        (is (empty?
             (filter #(> (count %) 3) boat-positions)))))
    (testing "moves are valid"
      (let [left-moves (map first crossing-plan)
            middle-moves (map second crossing-plan)
            right-moves (map last crossing-plan)]
        (reduce validate-move left-moves)
        (reduce validate-move middle-moves)
        (reduce validate-move right-moves )))))

