(ns strava_heatmap.core
  (:require 
    [strava_heatmap.pitch_finder :as pitch]
    [strava_heatmap.gpx_parser :as gpx]))

(defn add-two [a b]
  (+ a b))

(defn print-gpx [file]
  (->> file
    gpx/read-file-as-string
    gpx/xml-to-trkpts
    pitch/extreme-points
    pitch/pitch-size
    println))

(defn -main []
  (println "hello, World!")
  (print-gpx "resources/square.gpx")
  (println "goodbye, World!"))

