(ns euler23.core
  (:gen-class))


(defn proper-divisors [n]
  (filter #(zero? (mod n %)) (range 1 n)))

(defn abundant? [n]
  (< n (reduce + (proper-divisors n))))


(def abundant-nums (into #{} (filter abundant? (range 1 28124))))


(defrecord Result [abnums cnt])


(defn check-abundant [result n]

 (let [abns (if (abundant? n) (conj (:abnums result) n) (:abnums result) )]

    (if (not-any? #(contains? abns (- n %)) abns)
      (Result. abns  (+ (:cnt result) n) )
      (Result. abns  (:cnt result)))))


(defn -main
  ""
  [& args]

  (println (:cnt (reduce check-abundant (Result.  #{} 0) (range 1 28124)))))
