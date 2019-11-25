(ns tdd-clojure.core)

(defn game-over-all-fields?
  "Checks if a game is over due to all fields being taken. 
  `fields-taken` is the number of fields taken at this point in time."
  [fields-taken]
  (let [no-of-fields-in-game 9]
    (>= fields-taken no-of-fields-in-game fields-taken)))

(defn game-over-groups-of-fields?
  "Checks if a game is over due to a particular group of fields being taken by
  a player. Such groups can be columns, rows and diagonals
  `taken-vec` is the vector of results checking all groups of one type for
  fields taken."
  [taken-vec]
  (->> taken-vec
       (some true?)
       (nil?)
       (not)))