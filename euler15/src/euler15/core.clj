(ns euler15.core
  (:gen-class))

;0011
;
;2^2n

; 00 00


(defn count-bits [n l]
  (reduce + (map #(if (bit-test n %) 1 0) (range l))))

(defn fact [n]
  (reduce * (take n (iterate dec n))))

(defn nk [n k]
  (/ (fact n) (* (fact k) (fact (- n k)))))


(defn paths []
  (let [start 2r00000000001111111111]
    (filter #(= 10 (count-bits % 20)) (iterate inc start))))

(defn -main
  ""
  [& args]
  ;brute force
  (println (count (take-while #(< % 2r11111111111111111111) (paths))))
  ;easy fast way
  (println (nk 20 10))
  )
