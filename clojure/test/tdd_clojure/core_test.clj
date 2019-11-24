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