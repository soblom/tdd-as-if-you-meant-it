(ns tdd-clojure.core)


(defn- reduce-equal [e1 e2]
  (if (= e1 e2) e1 false))

(defn diagonal-lr [pos row] (get row pos))

(defn diagonal-rl [pos row] (get row (- (count row) pos 1)))

(defn- transpose [fields] (apply mapv vector fields))


(defn column-taken?
  "Checks if all `fields` in a column have the same value.
  If so, that value is returned, otherwise `false`."
  [fields]
  (some keyword? (doall (map #(reduce reduce-equal %) fields))))

(defn row-taken?
  "Checks if all `fields` in a column have the same value.
  If so, that value is returned, otherwise `false`."
  [fields]
  (column-taken? (transpose fields)))

;; TODO: Refactor so that the predicate has the two diagonals built-in.
(defn diagonal-taken?
  "Checks if all fields diagonally have the same value. The `diagonal` is
  defined as a function that returns the column index for the diagonal."
  [diagonal fields]
  (reduce reduce-equal
          (map-indexed diagonal fields)))

(defn all-taken? [fields]
  (not-any? nil? (flatten fields)))

(defn game-over? [fields]
  (true? (or (all-taken? fields)
             (column-taken? fields)
             (row-taken? fields)
             (diagonal-taken? diagonal-lr fields)
             (diagonal-taken? diagonal-rl fields))))

(defn field-taken? [fields nrow ncol]
  "Determines if the field of a `field` at the row `nrow` and column `ncol` is
  taken (i.e. not `nil`. If so, it returns `true` and `false` otherwise.
  The `field` is expected to be a vector of vectors."
  (if (some? (-> fields (get nrow) (get ncol))) true false))

(defn update-field [fields val nrow ncol]
  "'Updates' the value in field of a `field` with `val`."
  (let [row  (get fields nrow)
        field (get row ncol)]
   (-> (assoc fields nrow (assoc row ncol val)))))

(defn take-field! [board player nrow ncol]
    (if (not (field-taken? @board nrow ncol))
      (swap! board update-field player nrow ncol)
      nil))
