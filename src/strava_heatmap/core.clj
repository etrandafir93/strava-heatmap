(ns strava_heatmap.core
  (:require 
    [strava_heatmap.pitch_finder :as pitch]
    [strava_heatmap.heatmap :as heatmap]
    [strava_heatmap.gpx_parser :as gpx]))

(defn print-trkpts [file]
  (->> file
    gpx/read-file-as-string
    gpx/xml-to-trkpts
    heatmap/normalize-trkpts
    heatmap/heatmap-matrix
    println))


(defn -main []
  (print-trkpts "resources/square.gpx"))