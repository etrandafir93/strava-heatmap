(ns strava_heatmap.core
  (:import java.util.UUID)
  (:require 
    [strava_heatmap.pitch_finder :as pitch]
    [strava_heatmap.heatmap :as heatmap]  
    [strava_heatmap.gpx_parser :as gpx]
    [strava_heatmap.write_html :as html]))

(defn pretty-print [step-msg arg]
  (do
    (println (str "=== " step-msg " ==="))
    (println arg)
    (println "================================\n")
    arg))

(defn print-trkpts [file]
  (->> file
    (pretty-print "step 1: xml file")
    gpx/read-file-as-string
    (pretty-print "step 2: xml string")
    gpx/xml-to-trkpts
    (filter (fn[pt] (< (:cadence pt) 10)))
    (pretty-print "step 3: track points")
    (heatmap/normalize-trkpts)
    (pretty-print "step 4: normalized track points")
    heatmap/heatmap-matrix
    (pretty-print "step 5: heatmap matrix")
    heatmap/map-to-rgb
    (pretty-print "step 6: heatmap rgb")
    heatmap/map-to-json
    (pretty-print "step 7: heatmap json")
    (html/generate-html)
    (pretty-print "step 8: heatmap html")
    (html/save-file (str "out/" (UUID/randomUUID) ".html"))
    (pretty-print "step 9: saving html file")))

;; clojure -M:run resources/Evening_Run.gpx
(defn -main [& args]
  (if (empty? args)
    (println "No input file provided. Usage: clojure -M:run <gpx-file>")
    (let [input-file (first args)]    
      (print-trkpts input-file))))