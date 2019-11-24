(ns tdd-clojure.core-test 
  (:require
   [tdd-clojure.core :as tdd]
   [clojure.test :refer :all]))

;; Req1: A `game is over` when all `fields` are `taken`
(deftest test-game-over-when-all-fields-taken
  (let [no-of-fields-taken-all-taken 9
        no-of-fields-taken-some-taken 7]
    (is (= true
           (tdd/game-over-all-fields? no-of-fields-taken-all-taken)))
    (is (= false
           (tdd/game-over-all-fields? no-of-fields-taken-some-taken)))))

;; Req2: A `game is over` when `all fields in a column` are `taken` by one `player`
(deftest game-over-when-one-column-taken-by-player
  (let [fields-reduction-one-or-more-columns-taken-by-player [true false false]
        fields-reduction-no-column-taken-by-player [false false false]]
    (is (= true
           (tdd/game-over-column? 
              fields-reduction-one-or-more-columns-taken-by-player)))
    (is (= false
           (tdd/game-over-column? 
              fields-reduction-no-column-taken-by-player)))))