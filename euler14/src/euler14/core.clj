(ns euler14.core
  (:gen-class))


(defn next-number [n]
  (if (odd? n) (inc (* 3 n)) (/ n 2)))

(defn collatz-seq [n]
  (take-while #(not (= 1 %)) (iterate next-number n)))


;(apply (  (map #(vector % (count (collatz-seq %))) (range 13 100))



(defn -main
  "Find longest collatz-seq under 1,000,000"
  [& args]
  (println (reduce #(if (> (%1 1) (%2 1) ) %1 %2) (map #(vector % (inc (count (collatz-seq %)))) (range 1 1000000)))))
