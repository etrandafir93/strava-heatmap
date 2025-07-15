(ns strava_heatmap.core_test
  (:require [clojure.test :refer :all]
            [strava_heatmap.core :refer :all]))

(deftest test-add-two
  (testing "add-two adds numbers correctly"
    (is (= 3 (add-two 1 2)))
    (is (= 0 (add-two -1 1)))
    (is (= -5 (add-two -2 -3)))))
