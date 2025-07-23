(ns strava_heatmap.heatmap_test
  (:require [clojure.test :refer :all]
            [strava_heatmap.heatmap :as heatmap]))


(def min-lon (bigdec 26.0))
(def min-lat (bigdec 44.0))
(def max-lon (bigdec 26.1))
(def max-lat (bigdec 44.1))

(def dummy-trkpts
  [ { :longitude min-lon :latitude min-lat },
    { :longitude max-lon :latitude min-lat },
    { :longitude max-lon :latitude max-lat },
    { :longitude min-lon :latitude max-lat }])

(deftest test-normalize-trkpts
  (testing "should return correct normalized track points with precision 100: 
        eg: max-lon -> 100, max-lat -> 100, min-lon -> 0, min-lat -> 0"
    (is (=
      [{:x 0M, :y 0M} {:x 100M, :y 0M} {:x 100M, :y 100M} {:x 0M, :y 100M}]
      (heatmap/normalize-trkpts dummy-trkpts 100)))))


(deftest test-normalize-trkpts-precision-10
  (testing "should return correct normalized track points with precision 10: 
        eg: max-lon -> 10, max-lat -> 10, min-lon -> 0, min-lat -> 0"
    (is (=
      [{:x 0M, :y 0M} {:x 10M, :y 0M} {:x 10M, :y 10M} {:x 0M, :y 10M}]
      (heatmap/normalize-trkpts dummy-trkpts 10)))))