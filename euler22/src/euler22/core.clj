(ns euler22.core
  (:gen-class))


(def letter-scores {\A 1,
                    \B 2,
                    \C 3,
                    \D 4,
                    \E 5,
                    \F 6,
                    \G 7,
                    \H 8,
                    \I 9,
                    \J 10,
                    \K 11,
                    \L 12,
                    \M 13,
                    \N 14,
                    \O 15,
                    \P 16,
                    \Q 17,
                    \R 18,
                    \S 19,
                    \T 20,
                    \U 21,
                    \V 22
                    \W 23,
                    \X 24,
                    \Y 25,
                    \Z 26
                  })



(defn word-score[indx word]
 (* (inc indx) (reduce + (map #(letter-scores %) (seq word))  )))


(def result (reduce +
  (map-indexed word-score
    (sort #(.compareToIgnoreCase %1 %2)
          (apply list (.split (.replace (slurp "C:\\Users\\53873\\Desktop\\p022_names.txt") "\"" "" ) ",")))))  )

(defn -main
  ""
  [& args]
  (println "Solution is: " result))
