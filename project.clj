(defproject immutable-webapp "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [org.clojure/clojurescript "1.7.228"]
                 [org.clojure/core.async "0.2.374"]
                 [ring/ring-core "1.4.0"]
                 [ring/ring-jetty-adapter "1.4.0"]
                 [compojure "1.4.0"]
                 [org.webjars/bootstrap "3.3.6"]
                 [cljs-http "0.1.39"]
                 [liberator "0.14.0"]
                 [fogus/ring-edn "0.3.0"]
                 [clj-json "0.5.3"]
                 [hiccup "1.0.5"]
                 [reagent "0.5.1"]
                 [prismatic/schema "1.0.4"]
                 [com.datomic/datomic-free "0.9.5344"]]
  :plugins [[lein-ring "0.9.7"]
            [lein-cljsbuild "1.1.2"]]
  :source-paths ["src"]
  :cljsbuild {:builds [{:id "webapp"
                        :source-paths ["src-cljs"]
                        :compiler {:output-to "resources/public/js/main.js"
                                   :output-dir "resources/public/js"
                                   :optimizations :none
                                   :source-map true
                                   :parallel-build false
                                   :asset-path "js"
                                   :main "webapp.main"}}]}
  :clean-targets ^{:protect false} [:target-path :compile-path
                                    "resources/public/js"]
  :ring {:handler webapp.api/handler
         :nrepl {:start? true :port 4500}
         :port 8090}
  :global-vars {*print-length* 20})
