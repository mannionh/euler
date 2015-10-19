;;Lazy sieve of erathostenes
;;

(ns primesieve.core
  (:gen-class))


  (defn add-to-map [k v mp]
    (if (nil? (mp k))
      (assoc mp k (vector v))
      (assoc mp k (conj (mp k) v))))

(defn prime-multiples[init incby]
  "Lazy seq of muliples of p"
  (iterate (partial + incby) init))

  (defn move-to-nxt-fct [n primefacts]
    (reduce #(add-to-map (first (rest %2)) (rest %2) %1) primefacts (primefacts n)))


	(defn cross-off [n primefacts]
    (let [pf (primefacts n)]

      (if (boolean pf)
        [false (move-to-nxt-fct n primefacts)]
        [true primefacts] )))


  (defn add-prime [p factormap]
    ;;(println "Adding prime to factor map " p)
    (add-to-map (* p p) (prime-multiples (* p p) p) factormap ))


  (defn primesseq [p factormap]
    "Generate a lazy seq of prime numbers."

    (let [[isprime factors] (cross-off p factormap)]
      (if isprime
        (cons p (lazy-seq (primesseq (inc p) (add-prime p factors))))
        (primesseq (inc p) factors))))



(defn -main
  "Main"
  [& args]
  ;;Euler 10 solution sum or all primes under 2 million = 142913828922
  (println (reduce + (take-while #(< % 2000000) (primesseq 2 {}))))
  ;;

  (println (last (take 10001 (primesseq 2 {}) )))
  ;;(take 20 (primesseq 2 {})
  )
