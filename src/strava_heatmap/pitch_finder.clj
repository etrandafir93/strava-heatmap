(ns strava_heatmap.pitch_finder)

(defrecord Extremities [north south east west])
(defrecord PitchSize [width height])

(defn extreme-points [trkpts]
  (->Extremities
    (apply max (map :latitude trkpts))
    (apply min (map :latitude trkpts))
    (apply max (map :longitude trkpts))
    (apply min (map :longitude trkpts))))

(defn pitch-size [extremities]
  {:width (- (:east extremities) (:west extremities))
   :height (- (:north extremities) (:south extremities))})

(defn normalize-trkpts [trkpts]
  (let [extremities (extreme-points trkpts)]
    (map (fn [pt] {:latitude (- (:latitude pt) (:south extremities))
                   :longitude (- (:longitude pt) (:west extremities))
                   :time (:time pt)
                   :cadence (:cadence pt)})
         trkpts)))