(ns strava_heatmap.heatmap
  (:require [strava_heatmap.pitch_finder :as pitch])
  (:import java.math.BigDecimal)
  (:import java.math.RoundingMode))

(def PRECISION 100M)

(defn safe-divide [a b]
  (.divide (bigdec a) (bigdec b) 10 RoundingMode/HALF_UP))

(defn normalize-trkpts-precision [precision trkpts]
  (let [{:keys [south north west east]} (pitch/extreme-points trkpts)
        lat-range (if (not= south north) (- north south) 1)
        lon-range (if (not= west east) (- east west) 1)]
    (mapv (fn [pt]
            {:x (int (* precision (safe-divide (- (:longitude pt) west) lon-range)))
             :y (int (* precision (safe-divide (- (:latitude pt) south) lat-range)))})
          trkpts)))

(defn normalize-trkpts [trkpts]
  (normalize-trkpts-precision PRECISION trkpts))

(defn heatmap-matrix [normalized-trkpts]
  (reduce (fn [acc pt]
           (update acc [(:x pt) (:y pt)] (fn [current-val] (inc (or current-val 0)))))
         {}
         normalized-trkpts))

(defn map-to-rgb [matrix]
  (let [values (vals matrix)
        min-val (apply min values)
        max-val (apply max values)
        diff (if (= min-val max-val) 1 (- max-val min-val))]
    (into {} 
       (map (fn [[coords value]]
              [coords 
               (let [percent (double (/ (- value min-val) diff))
                     r (int (* 255 percent))
                     g (int (* 255 (- 1 percent)))
                     b 0]
                   (format "rgb(%d, %d, %d)" r g b))]))
            matrix)))

(defn map-to-json [heatmap-rgb]
  (str "["
       (clojure.string/join ","
         (map (fn [[[x y] color]]
                (str "{\"x\":" x ",\"y\":" y ",\"color\":\"" color "\"}"))
              heatmap-rgb))
       "]"))