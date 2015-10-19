(ns euler12.core
  (:gen-class))

(defn next-triangle [[index value]]
  [(inc index) (+ value (inc index))])

(defn get-divisors [n]
  (filter #(= 0 (mod n %)) (range 1 (inc (Math/sqrt n) ))))

;;(last (take-while #(< (count (get-divisors %)) 5) (map last (iterate next-triangle [1 1]))))

(defn -main
  [& args]
  (println (last (take-while #(< (count (get-divisors %)) 500) (map last (iterate next-triangle [1 1]))))))
