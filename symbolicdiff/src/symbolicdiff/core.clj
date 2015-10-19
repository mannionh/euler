(ns symbolicdiff.core
  (:gen-class))

(defn variable? [x]
  (symbol? x))

(defn same-variable? [x y]
  (and (variable? x) (variable? y) (= x y)))

(defn sum? [x]
  (and (seq? x) (= '+ (first x))))

(defn addend [x]
  (first (rest x)))

(defn augend [x]
  (last x))

(defn make-sum [x y]
  (cond (and (number? x) (= x 0)) y
        (and (number? y) (= y 0)) x
        (and (number? x) (number? y)) (+ x y)
  :else (list '+ x y) ))


(defn product? [x]
  (and (seq? x) (= '* (first x))))

(defn multiplier [x]
  (first (rest x)))

(defn multiplicand [x]
  (last x))

(defn make-product [x y]
  (cond (or (and (number? x) (= 0 x)) (and (number? y) (= 0 y)) ) 0
        (and (number? x) (= 1 x)) y
        (and (number? y) (= 1 y)) x
        (and (number? x) (number? y)) (* x y)
  :else (list '* x y)))


(defn exponentiation? [x]
  (and (seq? x) (= '** (first x))))

(defn base [x]
  (first (rest x)))

(defn exponent [x]
  (last x))

(defn make-exponentiation [x y]
  (case (= 0 y) 1
        (= 1 y) x
    :else (list '** x y)))



(defn deriv [exp var]
  (cond (number? exp) 0
        (variable? exp) (if (same-variable? exp var) 1 0)
        (sum? exp)
          (make-sum (deriv (addend exp) var) (deriv (augend exp) var))
        (product? exp)
          (make-sum
           (make-product (multiplier exp)
                         (deriv (multiplicand exp) var))
            (make-product (deriv (multiplier exp) var) (multiplicand exp) ))
        (exponentiation? exp)
          (make-product (exponent exp) (make-exponentiation (base exp) (+ -1 (exponent exp) )) ) ))


(defn -main
  "I don't do a whole lot ... yet."
  [& args]

  (println (deriv '(* 5 (** x 5)) 'x)))
