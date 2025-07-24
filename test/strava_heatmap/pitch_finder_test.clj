(ns strava_heatmap.pitch_finder_test
  (:require [clojure.test :refer :all]
            [strava_heatmap.pitch_finder :as pitch]))

(def dummy-trkpts
  [{
    :latitude 44.0M
    :longitude 26.0M
    ;; ommitting time and cadence for simplicity
    ;; :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    ;; :cadence 80
  },
  { :latitude 44.1M :longitude 26.0M },
  { :latitude 44.0M :longitude 26.0M },
  { :latitude 44.0M :longitude 26.1M }])

(deftest test-pitch-location
  (testing "should return correct extremities"
    (let [extremities (pitch/extreme-points dummy-trkpts)]
      (is (= 44.1M (:north extremities)))
      (is (= 44.0M (:south extremities)))
      (is (= 26.1M (:east extremities)))
      (is (= 26.0M (:west extremities))))))

(deftest test-pitch-size
  (testing "should return correct pitch size"
    (let [extremities (pitch/extreme-points dummy-trkpts)]
      (is (= 0.1M (:width (pitch/pitch-size extremities))))
      (is (= 0.1M (:height (pitch/pitch-size extremities)))))))

(deftest test-normalize-trkpts
  (testing "should return correct normalized track points"
    (let [normalized (pitch/normalize-trkpts dummy-trkpts)]
      (is (= 0.0M (:latitude (first normalized))))
      (is (= 0.0M (:longitude (first normalized))))
      (is (= 0.1M (:latitude (second normalized))))
      (is (= 0.1M (:longitude (nth normalized 3)))))))
