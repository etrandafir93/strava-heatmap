(ns strava_heatmap.heatmap
  (:require [strava_heatmap.pitch_finder :as pitch]))

(def PRECISION 100)

(defn normalize-trkpts-precision [trkpts precision]
  (let [{:keys [south north west east]} (pitch/extreme-points trkpts)
        lat-range (if (not= south north) (- north south) 1)
        lon-range (if (not= west east) (- east west) 1)]
    (mapv (fn [pt]
            {:x (int (* precision (/ (- (:longitude pt) west) lon-range)))
             :y (int (* precision (/ (- (:latitude pt) south) lat-range)))})
          trkpts)))

(defn normalize-trkpts [trkpts]
  (normalize-trkpts-precision trkpts PRECISION))

(defn heatmap-matrix [normalized-trkpts]
  (reduce (fn [acc pt]
           (update acc [(:x pt) (:y pt)] (fn [current-val] (inc (or current-val 0)))))
         {}
         normalized-trkpts))
