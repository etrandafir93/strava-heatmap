(ns strava_heatmap.core
  (:require [strava_heatmap.gpx_parser :as gpx]))

(defn add-two [a b]
  (+ a b))

(defn print-gpx [file]
  (gpx/pretty-print-gpx file))

(defn -main []
  (println "hello, World!")
  (print-gpx "resources/square.gpx")
  (println "goodbye, World!"))

