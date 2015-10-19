(ns euler21.core
  (:gen-class))

;;; Prime Sieve
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


  ;; end sieve


 ;;
 (def primes (set (take-while #(< % 10000)  (primesseq 2 {}) )))


 (defn is-prime? [n]
   (contains? primes n))



 (defn prime-factors[n factors]
   (if (is-prime? n) (cons n factors)
     (let [prm (first (filter #(zero? (mod n %)) primes))]
    (recur (/ n prm) (cons prm factors)))))

(defn pow [x y]
  (reduce * (repeat y x)))


 (defn sumterm [prm count]
   (/ (dec (pow prm (inc count))) (dec prm)))


 (defn sum-proper-divisors [n]
   (- (reduce * (map #(sumterm (key %) (val %) )
                  (frequencies (prime-factors n '())))) n))


(def proper-divisors (apply vector (map sum-proper-divisors (range 2 10000))))


(defn amicable? [a b]
  (if (= a b) false
        (and (= a (sum-proper-divisors b)) (= b (sum-proper-divisors a)))))

(defn get-amicable-pair [i vec-of-divisors]
  (let [idx (vec-of-divisors (- i 2))]
    ; i=220 idx=284 , i2=284 idy=220
    (if (and (> idx 1) (< (- idx 2) 10000))
     (if (and (not (= i idx)) (= i (vec-of-divisors (- idx 2)))) [i idx] ))))
    ;(if (= i idy) [idx idy])))





;(def nseq (iterate #(if (pos? %) (- %) (inc (- %)) ) 1))

;(defn pentagonal [x n]
;  (- x (/ (- (* 3 (* n n) ) n) 2)))


;(defn theta [n]
  ;(if (= n 0) n
  ;(map theta
      ; (take-while #(or (pos? %) (zero? %) ) (map (partial pentagonal n) nseq) ))


(defn -main
  ""
  [& args]
  ;(println ((sum-proper-divisors 1))) )
  (print (reduce + (into #{} (reduce concat (filter (comp not nil?) (map #(get-amicable-pair % proper-divisors) (range 2 10000) ))) ))))
