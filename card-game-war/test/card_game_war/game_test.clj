(ns card-game-war.game-test
  (:require [clojure.test :refer :all]
            [card-game-war.game :refer :all]))


;; fill in  tests for your game
(deftest test-play-round
  (testing "the highest rank wins the cards in the round"
    (let [spade4 [:spade 4]
          spade8 [:spade 8]]
      (is (= spade8 (play-round spade4 spade8))))
    )
  (testing "queens are higher rank than jacks"
    (let [queenspade [:spade :queen]
          jackspade [:spade :jack]]
      (is (= queenspade (play-round queenspade jackspade))))
    )
  (testing "kings are higher rank than queens"
    (let [queenspade [:spade :queen]
          kingspade [:spade :king]]
      (is (= kingspade (play-round queenspade kingspade))))
    )

  (testing "aces are higher rank than kings"
    (let [acespade [:spade :ace]
          kingspade [:spade :king]]
      (is (= acespade (play-round acespade kingspade))))
    )
  (testing "if the ranks are equal, clubs beat spades"
    (let [spade8 [:spade 8]
          club8 [:club 8]]
      (is (= club8 (play-round club8 spade8))))
    )
  (testing "if the ranks are equal, diamonds beat clubs"
    (let [diamond8 [:diamond 8]
          club8 [:club 8]]
      (is (= diamond8 (play-round club8 diamond8))))
    )
  (testing "if the ranks are equal, hearts beat diamonds")
    (let [heart8 [:heart 8]
          diamond8 [:diamond 8]]
      (is (= heart8 (play-round heart8 diamond8))))
    )

(deftest test-play-game
  (testing "the player loses when they run out of cards"))

