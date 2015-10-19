(ns euler17.core
  (:gen-class))

(def digits {1 "one", 2 "two", 3 "three", 4 "four",5 "five",6 "six",7 "seven",8 "eight",9 "nine",10 "ten"})

(def tens ["twenty", "thirty", "forty", "fifty", "sixty", "seventy", "eighty", "ninety"])

(def c ["hundred", "thosand"])



(defn num-to-english [n]
  (cond (< n 21) (digits n)



(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (println "Hello, World!"))
