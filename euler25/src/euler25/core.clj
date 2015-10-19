(ns euler25.core
  (:gen-class))


(defn next-fib-pair [[a b]]
  [b (+' a b)])

;;indexed seq of fibonacci numbers
(defn fib-seq []
  (map-indexed #(vector %1 %2) (map first (concat '([1 1]) (iterate next-fib-pair [1 2])))))



(defn -main
  "print index of and digit of thosandth fibonacci number"
  [& args]
  (println (first (filter #(>= (count (str (second %))) 1000) (fib-seq))) ))
