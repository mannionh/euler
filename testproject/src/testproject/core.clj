(ns testproject.core)


;;Any live cell with fewer than two live neighbours dies, as if caused by under-population.
;;Any live cell with two or three live neighbours lives on to the next generation.
;;Any live cell with more than three live neighbours dies, as if by overcrowding.
;;Any dead cell with exactly three live neighbours becomes a live cell, as if by reproduction.

(import
 '(java.awt Color Graphics Dimension)
 '(java.awt.image BufferedImage)
 '(javax.swing JPanel JFrame))

(def pairs [ [-1 0] [1 0] [-1 1] [0 1] [1 1] [-1 -1] [0 -1] [1 -1] ])

(def BOARD_SIZE 100)

(def board (ref  (into-array (map boolean-array (partition BOARD_SIZE (take (* BOARD_SIZE BOARD_SIZE) (repeatedly #(< (rand) 0.5))))))))

(defn sum-coord [a b]
  (let [ [x y] a [j k] b]
    [(+ x j) (+ y k)]))

(defn get-neighbours [x y]
  (filter #(let [[a b] %] (and (pos? a) (pos? b) (< a BOARD_SIZE) (< b BOARD_SIZE)))
        (map sum-coord pairs (repeat (count pairs) [x y])) ))

(defn num-alive-neighbours[x y array]
 (count (filter identity (map #(let [[a b] %] (aget array a b)) (get-neighbours x y)))   ))


(defn apply-rules [live-cell live-neighbours]
  (cond
   (and live-cell (< live-neighbours 2)) false
   (and live-cell (or (= live-neighbours 2) (= live-neighbours 3))) true
   (and live-cell (> live-neighbours 3)) false
   (and (not live-cell) (= 3 live-neighbours)) true
    :default false))

;;Next Gen on a point
(defn do-next-gen [point world-array]
  (let [[x y] point]
  (apply-rules (aget world-array x y) (num-alive-neighbours x y world-array))))


;;Move the world to the next state
(defn next-state [world-array]
  (dosync (ref-set board
  (into-array (map boolean-array
       (partition BOARD_SIZE
                  (map #(do-next-gen % world-array) (for [x (range BOARD_SIZE) y (range BOARD_SIZE)] [x y]))))))))


;;UI

(def dim BOARD_SIZE)
(def scale 5)
(def animation-sleep-ms 500)
(def running true)

(defn fill-cell [#^Graphics g x y c]
  (doto g
    (.setColor c)
    (.fillRect (* x scale) (* y scale) scale scale)))

(defn render-cell [g x y]

  (if (aget @board x y)
   (fill-cell g x y (. Color BLACK) )
  ))



(defn render [g]
  (let [img (new BufferedImage (* scale dim) (* scale dim) (. BufferedImage TYPE_INT_ARGB))
        bg (. img (getGraphics))]

    (doto bg
      (.setColor (. Color white))
      (.fillRect 0 0 (. img (getWidth)) (. img (getHeight))))
    (dorun
      (for [x (range dim) y (range dim)]
      (render-cell bg x y)
     ))
    (doto bg
      (.setColor (. Color blue))
     )
    (. g (drawImage img 0 0 nil))
    (. bg (dispose))

    ))


(def panel (doto (proxy [JPanel] []
                        (paint [g] (render g)))
             (.setPreferredSize (new Dimension
                                     (* scale dim)
                                     (* scale dim)))))

(def frame (doto (new JFrame) (.add panel) .pack (.setVisible true)))


(def animator (agent nil))

(defn animation [x]
  (when running
    (send-off *agent* #'animation))
  (next-state @board)
  (. panel (repaint))
  (. Thread (sleep animation-sleep-ms))
  nil)


(defn -main [& args]
  (do
    ;;(prn @board)
    ;;(next-state @board)
    ;;(prn (. java.util.Arrays (toString @board)))
    (send-off animator animation)
    ))



