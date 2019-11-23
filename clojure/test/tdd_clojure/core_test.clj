(ns tdd-clojure.core-test
  (:require [clojure.test :refer :all]
            [eftest.runner :as ef]
            [clojure.set :as set]
            [tdd-clojure.core :as tdd]))

;; REQ: A game of tic-tac-toe is over if all felds are covered
;;  -> fields are numbered 1 to 0 from top left to bottom right.
;;  -> There are two lists of fields, one for those taken by either player,
;;     and one for fields not yet taken.
(def all-fields-taken {:taken '(3 2 4 5 7 9 1 6 8)
                       :empty '()})

(def some-fields-empty {:taken '(3 2 4 5 9 1 6)
                        :empty '(7 8)})

(deftest game-over-when-all-fields-taken
  (is (= true
         (tdd/all-taken? all-fields-taken)))
  "all fields covered means game over"
  (is (= false
         (tdd/all-taken? some-fields-empty))))

;; REQ: a game is over when all fields in a row are taken by a player
;;   -> We need to know which fields are taken by which player (and which ones
;;      are empty). We keep three lists for this.
;;   -> There are three colums: The fields (1,2,3), the fields (4,5,6) and
;;      the fields (7,8,9).

(def first-row-p1 {:p1 '(5 4 3 6) ;; (4,5,6) = second row
                   :p2 '(2 9 1)
                   :empty '(7 8)})

(def no-row-taken {:p1 '(9,4,3,6)
                   :p2 '(2,5,1)
                   :empty '(7,8)})

(deftest game-over-when-one-row-taken
  (is (= true
         (tdd/row-taken? first-row-p1)))
  (is (= nil
         (tdd/row-taken? no-row-taken))))

;; REQ: A game is over when all fields in a column are taken by a player
;;   -> Same exercise as with rows, just for the fields (1,4,7), (2,5,8) and
;;      (3,6,9)

(def first-col-p1 {:p1 '(7 5 4 1) ;; (4,5,6) = second row
                   :p2 '(2 9 3)
                   :empty '(7 8)})

(def no-col-taken {:p1 '(9,4,3,6)
                   :p2 '(2,5,1)
                   :empty '(7,8)})

(defn col-taken? [fields]
  (let [r1 #{1 4 7} r2 #{2 5 8} r3 #{3 6 9}]
    (letfn [(rows? [fs]
              (or (set/subset? r1 fs)
                  (set/subset? r2 fs)
                  (set/subset? r3 fs)))]
      (rows? (set (:p1 fields))))))

(deftest game-over-when-column-taken-by-player
  (is (= true
         (col-taken? first-col-p1))
      (= nil
         (col-taken? no-col-taken))))

;; REQ: a game is over when all fields in a diagonal are taken by a player
;;   -> This now requires the field to be a square, i.e. equal numbers of
;;      rows and columns.
;;   -> Assume "a diagonal" to mean the full diagonal from (1,1) to (n,n) or
;;      from (n,1) to (1,n).

(defn field-matcher [subsets]
  (fn [fields] (reduce)))

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

;;a player can take a field if not already taken

(def board-with-empty-fields [[:p1 nil nil]
                              [nil :p1 :p1]
                              [:p1 :p1 :p1]])

(deftest update-empty-field
  (testing "update on empty field returns updated field"
    (is (= [[:p1 :p1 nil] ;at[0][1] expect change to :p1
            [nil :p1 :p1]
            [:p1 :p1 :p1]]
           (tdd/update-field board-with-empty-fields :p1 0 1))))
  (testing "update on taken field returns nil"
    (is (= board-with-empty-fields
           (tdd/update-field board-with-empty-fields :p1 2 2)))))

(deftest take-field-allows-update-on-field-only-first-time
  (let [board (atom board-with-empty-fields)
        resulting-field [[:p1 :p1 nil]
                         [nil :p1 :p1]
                         [:p1 :p1 :p1]]]
      (is (= resulting-field
             (tdd/take-field! board :p1 0 1)))
      (is (= nil
             (tdd/take-field! board :p1 0 1)))
      (is (= resulting-field
             @board))))

;;players take turns taking fields until the game is over
;;
;; -> Take Turns: If the last move was from P1, then the next move must be considered to be
;;    to be from P2 and vice versa
;; -> Game Over: This continues, until one of the "Game Over" conditions is met
(def game-won-by-p1 {:moves '(5, 9, 4, 1, 6)
                     :last :p1})
;
; (deftest move-by-p2-after-p1-is-succesful
;   (letfn [])
;   (is (= (map update-field
;            tdd/game-over? game-won-by-p1)
;          :p1)))
