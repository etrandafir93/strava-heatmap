(ns strava_heatmap.core
  (:require 
    [strava_heatmap.pitch_finder :as pitch]
    [strava_heatmap.heatmap :as heatmap]
    [strava_heatmap.gpx_parser :as gpx]))

(defn trkpts [file]
  (->> file
    gpx/read-file-as-string
    gpx/xml-to-trkpts))

(defn print-gpx [file]
  (let [trkpts (trkpts file)]
    (let [extemities (pitch/extreme-points trkpts)]
      (let [pitch-size (pitch/pitch-size extemities)]
        (println (heatmap/normalize-trkpts trkpts 100))))))

(defn -main []
  (print-gpx "resources/square.gpx"))