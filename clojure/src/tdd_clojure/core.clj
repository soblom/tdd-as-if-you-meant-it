(ns tdd-clojure.core)

(defn game-over-all-fields?
  "Checks if a game is over due to all fields being taken. 
  `fields-taken` is the number of fields taken at this point in time."
  [fields-taken]
  (let [no-of-fields-in-game 9]
    (>= fields-taken no-of-fields-in-game fields-taken)))