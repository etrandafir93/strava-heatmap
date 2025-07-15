(ns strava_heatmap.gpx_parser_test
  (:require [clojure.test :refer :all]
            [strava_heatmap.gpx_parser :as gpx]))

(defn to-instant [strings]
  (map (fn [it] (java.time.Instant/parse it)) strings))

(deftest parsing-longitude
  (testing "parse square.gpx and verify longitude"
    (is (=
      [26.0 26.001 26.001 26.0]
      (map (fn [it] (:longitude it))
        (gpx/file-to-trkpts "resources/square.gpx"))))))

(deftest parsing-latitude
  (testing "parse square.gpx and verify latitude"
    (is (=
      [44.0 44.0 44.001 44.001]
      (map (fn [it] (:latitude it))
        (gpx/file-to-trkpts "resources/square.gpx"))))))
  
(deftest parsing-cadence
  (testing "parse square.gpx and verify cadence"
    (is (=
      [80 85 90 95]
      (map (fn [it] (:cadence it))
        (gpx/file-to-trkpts "resources/square.gpx"))))))


(deftest parsing-time
  (testing "parse square.gpx and verify time"
    (is (=
      (to-instant ["2025-07-11T16:49:57Z" "2025-07-11T16:50:00Z" "2025-07-11T16:50:03Z" "2025-07-11T16:50:06Z"])
      (map (fn [it] (:time it))
        (gpx/file-to-trkpts "resources/square.gpx"))))))
