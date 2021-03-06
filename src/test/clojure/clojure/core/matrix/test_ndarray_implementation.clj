(ns clojure.core.matrix.test-ndarray-implementation
  (:use clojure.test)
  (:use clojure.core.matrix)
  (:require [clojure.core.matrix.operators :as op])
  (:require [clojure.core.matrix.compliance-tester])
  (:require clojure.core.matrix.impl.persistent-vector)
  (:use clojure.core.matrix.impl.ndarray))

(deftest regressions
  (let [m (make-ndarray [2 2])
        vm [[1 2] [3 4]]]
    (is (thrown? Throwable (esum m)))
    (assign! m vm)
    (is (= vm (coerce [] m)))
    (is (== 10 (esum m)))))

(deftest test-ndarray-base
  (testing "construction"
    (is (= [3 3] (seq (shape (make-ndarray [3 3]))))))
  (testing "getters"
    (is (= nil (mget (make-ndarray [3 3]) 2 2)))
    (is (= nil (mget (make-ndarray [3 3 3]) 1 1 1))))
  (testing "setters"
    (let [m (make-ndarray [2 2])]
      (mset! m 0 0 1)
      (is (== 1.0 (mget m 0 0))))))

(deftest test-assign
  (let [m (make-ndarray [2 2 2])
        vm [[[0 1] [2 3]] [[4 5] [6 7]]]]
    (assign! m vm)
    (is (= (eseq m) (range 8))))) 

(deftest test-helper-functions
  (is (== 35 (calc-index [1 5] (long-array [100 30]))))
  (is (== 10101 (calc-index [1 1 1] (long-array [200 100 100]))))) 

;; run complicance tests
(deftest compliance-test
   (clojure.core.matrix.compliance-tester/compliance-test (make-ndarray [3 3])))
