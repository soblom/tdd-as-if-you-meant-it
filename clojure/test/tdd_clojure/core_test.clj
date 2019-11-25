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
           (tdd/game-over-groups-of-fields? 
              fields-reduction-one-or-more-columns-taken-by-player)))
    (is (= false
           (tdd/game-over-groups-of-fields? 
              fields-reduction-no-column-taken-by-player)))))

;; Req3: A `game is over` when `all fields in a row` are `taken` by one `player`

; After re-using `game-over-column?` for rows:
; Test works without refactoring => We have said nothing about rows vs cols.
; This will obviously be true for the diagonals: Given a vector with booleans
;   that represent whether the diagonals are taken will lead to the same result.

; What makes rows and cols different? The fields they are made of!
;   In case of rows:  Top 3 fields    = Row 1
;                     Mid 3 fields    = Row 2
;                     Low 3 fields    = Row 3

;   Whereas for cols: Left   3 fields = Col 1
;                     Middle 3 fields = Col 2
;                     Right  3 fields = Col 3

; We have also not said anything about 'fields taken by player'.
; What does it mean that a field is taken by player? How do we represent fields
; and how do we represent their 'taken by' or 'not taken' status?

(deftest game-over-when-one-row-taken-by-player
  (let [fields-reduction-one-or-more-rows-taken-by-player [true false false]
        fields-reduction-no-row-taken-by-player [false false false]]
    (is (= true
           (tdd/game-over-groups-of-fields? 
            fields-reduction-one-or-more-rows-taken-by-player)))
    (is (= false
           (tdd/game-over-groups-of-fields? 
            fields-reduction-no-row-taken-by-player)))))
