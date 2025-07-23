(ns strava_heatmap.heatmap
  (:require [strava_heatmap.pitch_finder :as pitch]))

(def PRECISION 100)

(defn normalize-trkpts [trkpts precision]
  (let [{:keys [south north west east]} (pitch/extreme-points trkpts)
        lat-range (if (not= south north) (- north south) 1)
        lon-range (if (not= west east) (- east west) 1)]
    (mapv (fn [pt]
            {:x (* precision (/ (- (:longitude pt) west) lon-range))
             :y (* precision (/ (- (:latitude pt) south) lat-range))})
          trkpts)))
