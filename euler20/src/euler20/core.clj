(ns euler20.core
  (:gen-class))

(defn fact [n]
  (reduce *' (take n (iterate dec n))))

(defn sum-of_fact-digits [n]
  (reduce + (map #(bigint (str %)) (seq (str (fact n))))))

(defn -main
  ""
  [& args]
  (println (sum-of_fact-digits 100)))
