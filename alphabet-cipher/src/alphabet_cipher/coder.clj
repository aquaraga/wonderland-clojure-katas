(ns alphabet-cipher.coder)

(def alphabets "abcdefghijklmnopqrstuvwxyz")

(defn encode-substitute [row col]
  (let [not-row (partial not= row)
        subst-str (take 26 (drop-while not-row (cycle (sequence alphabets))))
        subst-map (zipmap alphabets subst-str)]
    (get subst-map col)))


(defn encode [keyword message]
  (apply str (map encode-substitute (cycle keyword) message)))

(defn decode [keyword message]
  "decodeme")

(defn decipher [cipher message]
  "decypherme")

