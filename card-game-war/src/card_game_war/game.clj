(ns card-game-war.game)

;; feel free to use these cards or use your own data structure
(def suits [:spade :club :diamond :heart])
(def ranks [2 3 4 5 6 7 8 9 10 :jack :queen :king :ace])
(def cards
  (for [suit suits
        rank ranks]
    [suit rank]))

(def face-values {:jack 11 :queen 12 :king 13 :ace 14})
(def suit-values {:spade 1 :club 2 :diamond 3 :heart 4})

(defn play-round [[suit1 rank1 :as player1-card]
                  [suit2 rank2 :as player2-card]]
  (let [rank-val #(if (integer? %) % (get face-values % 0))
        rank1-val (rank-val rank1)
        rank2-val (rank-val rank2)
        suit1-val (get suit-values suit1)
        suit2-val (get suit-values suit2)]
    (cond (> rank1-val rank2-val) player1-card
          (< rank1-val rank2-val) player2-card
          (= rank1-val rank2-val)
            (if (> suit1-val suit2-val) player1-card player2-card))))

(defn play-game [player1-cards player2-cards])
