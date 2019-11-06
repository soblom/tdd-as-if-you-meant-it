(ns tdd-clojure.core-test
  (:require [clojure.test :refer :all]
            [tdd-clojure.core :as tdd]))

;; REQ: A game of tic-tac-toe is over if all felds are covered
;;  -> There is a collection of fields
;;  -> Fields can be marked as taken
(def all-fields-taken [[:p1 :p2 :p3] [:p1 :p1 :p2] [:p1 :p1 :p2]])

(deftest game-over-when-all-fields-taken
  (is (= true (tdd/all-taken? all-fields-taken)))
  "all fields covered means game over")

;; REQ: A game is over when all fields in a column are taken by a player
;;   -> "column" implicates that the field is rectangular, ie. each column
;;      appears in each row.
;; Given a column, we can assume that the shape of the playing field is rectangular

(def one-column-taken-by-player [[:p1 :p2 :p3]
                                 [:p1 :p1 :p1] ;; all fields taken by p1
                                 [:p2 :p1 :p2]
                                 [:p1 nil :p2]])

(def no-column-taken-by-player [[:p1 :p2 :p3]
                                [:p1 :p2 :p1] ;; all fields taken by p1
                                [:p2 :p1 :p2]
                                [:p1 nil :p2]])

(deftest game-over-when-column-taken-by-player
  (is (= true
         (tdd/column-taken? one-column-taken-by-same-player))
   "one column taken by a player means game over"))

;; REQ: a game is over when all fields in a row are taken by a player
(def one-row-taken-by-player [[:p1 :p2 :p3]
                              [:p1 :p1 :p1]
                              [:p1 :p1 :p2]
                              [:p1 nil :p2]])

(deftest game-over-when-row-taken-by-player
  (is (= true
         (tdd/row-taken? one-row-taken-by-same-player)))
  "one row taken by a player means game over")

;; REQ: a game is over when all fields in a diagonal are taken by a player
;;   -> This now requires the field to be a square, i.e. equal numbers of
;;      rows and columns.
;;   -> Assume "a diagonal" to mean the full diagonal from (1,1) to (n,n) or
;;      from (n,1) to (1,n).

(def one-diagonal-taken-by-player [[:p1 :p2 :p3]
                                   [nil :p1 :p1]
                                   [:p1 :p1 :p1]])

(def another-diagonal-taken-by-player [[:p2 :p2 :p2]
                                       [nil :p2 :p1]
                                       [:p2 :p1 :p1]])


(deftest game-over-when-diagonal-taken-by-player
  (is (= :p1
         (tdd/diagonal-taken? tdd/diagonal-lr
                              one-diagonal-taken-by-player)))

  (is (= :p2
         (tdd/diagonal-taken? tdd/diagonal-rl
                              another-diagonal-taken-by-player)))

  (is (= false
         (tdd/diagonal-taken? tdd/diagonal-lr
                              all-fields-taken))))