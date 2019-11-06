(ns tdd-clojure.core)


(defn- reduce-equal [e1 e2]
  (if (= e1 e2) e1 false))

(defn- diagonal-lr [pos row] (get row pos))

(defn- diagonal-rl [pos row] (get row (- (count row) pos 1)))

(defn- transpose [fields] (apply mapv vector fields))


(defn column-taken?
  "Checks if all `fields` in a column have the same value.
  If so, that value is returned, otherwise `false`."
  [fields]
  (some keyword? (doall (map #(reduce reduce-equal %) fields))))

(defn row-taken? [fields] (column-taken? (transpose fields)))

(defn diagonal-taken? [diagonal fields]
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
