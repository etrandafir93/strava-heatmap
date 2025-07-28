(ns strava_heatmap.write_html
  (:require [clojure.java.io :as io]))

(defn generate-html-size
  "Generate an HTML visualization from a heatmap by replacing placeholders in the template"
  [size heatmap-json]
  (let [template-path "resources/template.html"
        template (slurp template-path)
        with-size (clojure.string/replace template "{SIZE}" (str size))
        with-data (clojure.string/replace with-size "{HEATMAP}" heatmap-json)]
    with-data))

(defn generate-html [heatmap-json]
  (generate-html-size 100 heatmap-json))

(defn save-file [path content]
  (spit path content)
  path) 