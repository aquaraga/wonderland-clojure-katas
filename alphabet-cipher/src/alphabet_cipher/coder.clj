(ns alphabet-cipher.coder)

(def alphabets "abcdefghijklmnopqrstuvwxyz")

(defn encode-substitute [row col]
  (let [not-row (partial not= row)
        subst-str (take 26 (drop-while not-row (cycle (sequence alphabets))))
        subst-map (zipmap alphabets subst-str)]
    (get subst-map col)))

(defn decode-substitute [row col]
  (let [not-row (partial not= row)
        subst-str (take 26 (drop-while not-row (cycle (sequence alphabets))))
        subst-map (zipmap subst-str alphabets)]
    (get subst-map col)))


(defn encode [keyword message]
  (apply str (map encode-substitute (cycle keyword) message)))


(defn decode [keyword message]
  (apply str (map decode-substitute (cycle keyword) message)))

(defn decipher [cipher message]
  (let [rep-keyword (apply str (map decode-substitute message cipher))
        rep-keyword-len (count rep-keyword)
       prefixes-array (map #(subs rep-keyword 0 (inc %)) (range rep-keyword-len))
        padded-prefix-array (map #(vector % (apply str (take rep-keyword-len (cycle %))))
                                 prefixes-array)
        matching-tuple (first (filter #(= rep-keyword (second %)) padded-prefix-array))]
    (first matching-tuple)))