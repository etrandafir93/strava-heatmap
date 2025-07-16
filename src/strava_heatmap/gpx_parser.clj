(ns strava_heatmap.gpx_parser
  (:require [clojure.java.io :as io]))

(defrecord TrackPoint [latitude longitude time cadence])

(defn read-file-as-string [file]
  (slurp (io/file file)))

(defn trkpt-element-pattern[]
  #"<trkpt lat=\"([^\"]*)\" lon=\"([^\"]*)\">[\s\S]*?<time>([^<]*)</time>[\s\S]*?<gpxtpx:cad>([^<]*)</gpxtpx:cad>")

(defn xml-to-trkpts 
  "Parses GPX XML string into a vector of TrackPoint records using regex."
  [xml-string]
  (let [valid-element (re-seq (trkpt-element-pattern) xml-string)]
    (mapv (fn [[_ latitude longitude time cadence]]
        (->TrackPoint 
          (bigdec latitude) 
          (bigdec longitude) 
          (java.time.Instant/parse time)
          (Integer/parseInt cadence)))
      valid-element)))

(defn file-to-trkpts [file]
  (xml-to-trkpts 
    (read-file-as-string file)))

(defn pretty-print-gpx [file]
  (println 
    (map :longitude
      (file-to-trkpts file)))) 
