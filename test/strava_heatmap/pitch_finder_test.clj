(ns strava_heatmap.pitch_finder_test
  (:require [clojure.test :refer :all]
            [strava_heatmap.pitch_finder :as pitch]))

(defn dummy-trkpts []
  [{
    :latitude 44.0
    :longitude 26.0
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  },
  { 
    :latitude 44.1
    :longitude 26.0
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  },
  { 
    :latitude 44.0
    :longitude 26.0
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  },
  { 
    :latitude 44.0
    :longitude 26.1
    :time (java.time.Instant/parse "2025-07-11T16:49:57Z")
    :cadence 80
  }])


(deftest test-pitch-location
  (testing "should return correct extremities"
    (is (=
      {:north 44.1
       :south 44.0
       :east 26.1
       :west 26.0}
      (pitch/extreme-points (dummy-trkpts))))))

