(ns euler24.core
  (:gen-class))




;;Find largest index i such that array[i − 1] < array[i].
(defn get-i [coll]
  (first (second (last (filter #(< (second (first %)) (second (second %)))
          (partition 2 1 (map-indexed vector coll)))))))


;;Find largest index j such that j ≥ i and array[j] > array[i − 1].
(defn get-j [coll i iminusoneval]
  (first (last (filter #(and (> (second %) iminusoneval) (>= (first %) i))
         (map-indexed vector coll)  ))) )


(defn swap [v i1 i2]
   (assoc v i2 (v i1) i1 (v i2)))


(defn reverse-from [i v]
  (apply vector (concat (subvec v 0 i) (reverse (subvec v i)))))



(defn next-perm[col]
  (let [i (get-i col) j (get-j col i (col (dec i)))]
    (reverse-from i (swap col (dec i) j))))






(defn -main
  "Print the millionth lexical permutation of 0 1 2 3 4 5 6 7 8 9"
  [& args]
  (println (last (take 1000000 (iterate next-perm [0 1 2 3 4 5 6 7 8 9])))))
