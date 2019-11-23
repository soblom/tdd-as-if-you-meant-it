(ns tdd-clojure.core
  (:require [clojure.set :as set]))


(defn- reduce-equal [e1 e2]
  (if (= e1 e2) e1 false))

(defn diagonal-lr [pos row] (get row pos))

(defn diagonal-rl [pos row] (get row (- (count row) pos 1)))

(defn- transpose [fields] (apply mapv vector fields))

(defn all-taken? [fields] (empty? (fields :empty)))

(defn column-taken?
  "Checks if all `fields` in a column have the same value.
  If so, that value is returned, otherwise `false`."
  [fields]
  (some keyword? (doall (map #(reduce reduce-equal %) fields))))

(defn split-rows [moves]
  (let [sorted (sort moves)
        [r1 cdr] (split-with #(< % 4) sorted)
        [r2 r3] (split-with #(< % 7) cdr)]
    [r1 r2 r3]))

(defn row-taken? [moves]
  "Tests whether any row is taken by either of the players."
  (let [{f-p1 :p1 f-p2 :p2} moves]
    (letfn [(full-row? [fields]
                       (some true? (map #(= 3 (count %))
                                        (split-rows fields))))]
      (or (full-row? f-p1) (full-row? f-p2)))))

;; TODO: Refactor so that the predicate has the two diagonals built-in.
(defn diagonal-taken?
  "Checks if all fields diagonally have the same value. The `diagonal` is
  defined as a function that returns the column index for the diagonal."
  [diagonal fields]
  (reduce reduce-equal
          (map-indexed diagonal fields)))

(defn game-over? [fields]
  (true? (or (all-taken? fields)
             (column-taken? fields)
             (row-taken? fields)
             (diagonal-taken? diagonal-lr fields)
             (diagonal-taken? diagonal-rl fields))))

(defn field-taken?
  "Determines if the `field` at the row `nrow` and column `ncol` is taken.
  Taken is defined as being not `nil`. If so, it returns `true` and `false` otherwise.
  The `field` is expected to be a vector of vectors."
  [fields nrow ncol]
  (if (some? (-> fields (get nrow) (get ncol))) true false))

(defn update-field
  "'Updates' the value in field of a `field` with `val`."
  [fields val nrow ncol]
  (let [row  (get fields nrow)
        field (get row ncol)]
    (-> (assoc fields nrow (assoc row ncol val)))))

(defn take-field! [board player nrow ncol]
    (if (not (field-taken? @board nrow ncol))
      (swap! board update-field player nrow ncol)
      nil))
