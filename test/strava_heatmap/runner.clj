(ns strava_heatmap.runner
  (:require [clojure.test :refer :all]
            [strava_heatmap.core_test]
            [strava_heatmap.gpx_parser_test]
            [strava_heatmap.pitch_finder_test]))

(defn -main []
  (run-tests 'strava_heatmap.core_test
             'strava_heatmap.gpx_parser_test
             'strava_heatmap.pitch_finder_test))
