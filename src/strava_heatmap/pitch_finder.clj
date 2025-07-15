(ns strava_heatmap.pitch_finder)

(defn northernmost [trkpts]
  (apply max (map :latitude trkpts)))

(defn southernmost [trkpts]
  (apply min (map :latitude trkpts)))

(defn easternmost [trkpts]
  (apply max (map :longitude trkpts)))

(defn westernmost [trkpts]
  (apply min (map :longitude trkpts)))

(defn extreme-points [trkpts]
  {:north (northernmost trkpts)
   :south (southernmost trkpts)
   :east (easternmost trkpts)
   :west (westernmost trkpts)})
