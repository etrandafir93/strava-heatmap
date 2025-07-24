(ns strava_heatmap.write_html
  (:require [clojure.java.io :as io]))

(defn generate-html
  "Generate an HTML visualization from a heatmap by replacing placeholders in the template"
  [heatmap-json & {:keys [size] :or {size 100}}]
  (let [template-path "resources/template.html"
        template (slurp template-path)
        with-size (clojure.string/replace template "{SIZE}" (str size))
        with-data (clojure.string/replace with-size "{HEATMAP}" heatmap-json)]
    with-data))

(defn save-file [path content]
  (spit path content)
  path) 