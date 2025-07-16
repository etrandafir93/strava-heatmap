(ns strava_heatmap.pitch_finder_test
  (:require [clojure.test :refer :all]
            [strava_heatmap.pitch_finder :as pitch]))

(def dummy-trkpts
  [{
    :latitude (bigdec 44.0)
    :longitude (bigdec 26.0)
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  },
  { 
    :latitude (bigdec 44.1)
    :longitude (bigdec 26.0)
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  },
  { 
    :latitude (bigdec 44.0)
    :longitude (bigdec 26.0)
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  },
  { 
    :latitude (bigdec 44.0)
    :longitude (bigdec 26.1)
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  }])

(deftest test-pitch-location
  (testing "should return correct extremities"
    (let [extremities (pitch/extreme-points dummy-trkpts)]
      (is (=
        (bigdec 44.1)
        (:north extremities)))
      (is (=
        (bigdec 44.0)
        (:south extremities)))
      (is (=
        (bigdec 26.1)
        (:east extremities)))
      (is (=
        (bigdec 26.0)
        (:west extremities))))))

(deftest test-pitch-size
  (testing "should return correct pitch size"
    (let [extremities (pitch/extreme-points dummy-trkpts)]
      (is (=
        (bigdec 0.1)
        (:width
          (pitch/pitch-size 
            extremities))))
      (is (=
        (bigdec 0.1)
        (:height
          (pitch/pitch-size 
            extremities)))))))

(deftest test-normalize-trkpts
  (testing "should return correct normalized track points"
    (let [normalized (pitch/normalize-trkpts dummy-trkpts)]
      (is (=
        (bigdec 0.0)
        (:latitude (first normalized))))
      (is (=
        (bigdec 0.0)
        (:longitude (first normalized))))
      (is (=
        (bigdec 0.1) 
        (:latitude (second normalized))))
      (is (=
        (bigdec 0.1) 
        (:longitude (nth normalized 3)))))))
