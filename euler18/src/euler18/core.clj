(ns euler18.core
  (:gen-class))


(def triangle (reverse (list
(list 75)
(list 95 64)
(list 17 47 82)
(list 18 35 87 10)
(list 20 04 82 47 65)
(list 19 01 23 75 03 34)
(list 88 02 77 73 07 63 67)
(list 99 65 04 28 06 16 70 92)
(list 41 41 26 56 83 40 80 70 33)
(list 41 48 72 33 47 32 37 16 94 29)
(list 53 71 44 65 25 43 91 52 97 51 14)
(list 70 11 33 28 77 73 17 78 39 68 17 57)
(list 91 71 52 38 17 14 91 43 58 50 27 29 48)
(list 63 66 04 68 89 53 67 30 73 16 69 87 40 31)
(list 04 62 98 27 23 9 70 98 73 93 38 53 60 04 23))))



(defn maximise [parent child-pair]
  (max (+ parent (nth child-pair 0)) (+ parent (nth child-pair 1))))



(defn process-item [idx parent-list child-list]
   (let [x (nth parent-list idx)]
    (maximise x (vector (nth child-list idx) (nth child-list (inc idx))))))



  (defn calculate-max [list-of-lists]
    (if (= 1 (count list-of-lists)) (nth list-of-lists 0)
      (let [chld (first list-of-lists) prnt (nth list-of-lists 1)]
       (calculate-max (cons (map #(process-item % prnt chld)  (range 0 (dec (count chld)))) (rest (rest list-of-lists)))))))



(defn -main
  ""
  [& args]
  (println (calculate-max triangle)  ))
