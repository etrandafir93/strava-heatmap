(ns strava_heatmap.heatmap_test
  (:require [clojure.test :refer :all]
            [strava_heatmap.heatmap :as heatmap]))


(def min-lon 26.0M)
(def min-lat 44.0M)
(def max-lon 26.1M)
(def max-lat 44.1M)

(def four-ponits-square
  [ { :longitude min-lon :latitude min-lat },
    { :longitude max-lon :latitude min-lat },
    { :longitude max-lon :latitude max-lat },
    { :longitude min-lon :latitude max-lat }])

(def seven-ponits
  (conj four-ponits-square 
    {:longitude min-lon :latitude min-lat}
    {:longitude min-lon :latitude min-lat}
    {:longitude max-lon :latitude max-lat}))

(deftest test-normalize-trkpts
  (testing "should return correct normalized track points with precision 100: 
        eg: max-lon -> 100, max-lat -> 100, min-lon -> 0, min-lat -> 0"
    (is (=
      [{:x 0, :y 0} {:x 100, :y 0} {:x 100, :y 100} {:x 0, :y 100}]
      (heatmap/normalize-trkpts four-ponits-square)))))


(deftest test-normalize-trkpts-precision-10
  (testing "should return correct normalized track points with precision 10: 
        eg: max-lon -> 10, max-lat -> 10, min-lon -> 0, min-lat -> 0"
    (is (=
      [{:x 0, :y 0} {:x 10, :y 0} {:x 10, :y 10} {:x 0, :y 10}]
      (heatmap/normalize-trkpts-precision four-ponits-square 10)))))

(deftest test-heatmap-matrix
  (testing "should return correct heatmap matrix"
    (is (=
      { [0 0] 1, [10 10] 1, [10 0] 1, [0 10] 1 }
      (heatmap/heatmap-matrix 
        (heatmap/normalize-trkpts-precision four-ponits-square 10))))))

(deftest test-heatmap-matrix
  (testing "should return correct heatmap matrix (prcision = 99)"
    (is (=
      { [0 0] 1, [99 99] 1, [99 0] 1, [0 99] 1 }
      (heatmap/heatmap-matrix 
        (heatmap/normalize-trkpts-precision four-ponits-square 99))))))