(defproject immutable-webapp "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/clojurescript "0.0-2322"]
                 [org.clojure/core.async "0.1.267.0-0d7780-alpha"]
                 [ring/ring-core "1.3.1"]
                 [ring/ring-jetty-adapter "1.3.1"]
                 [om "0.7.1"]
                 [ankha "0.1.4"]
                 [org.webjars/react "0.11.1"]
                 [org.webjars/bootstrap "3.2.0"]
                 [cljs-http "0.1.14"]
                 [compojure "1.1.8"]
                 [liberator "0.12.0"]
                 [com.taoensso/timbre "3.2.1"]
                 [fogus/ring-edn "0.2.0"]
                 [domina "1.0.2"]
                 [clj-json "0.5.3"]
                 [hiccup "1.0.5"]]
  :plugins [[lein-ring "0.8.10"]
            [lein-cljsbuild "1.0.4-SNAPSHOT"]]
  :source-paths ["src"]
  :cljsbuild {
    :builds [{:id "webapp"
              :source-paths ["src"]
              :compiler {
                :output-to "resources/public/main.js"
                :output-dir "resources/public/out"
                :optimizations :none
                :source-map true}}]}
  :ring {:handler webapp.api/handler
         :nrepl {:start? true :port 4500}
         :port 8090}
  :global-vars {*print-length* 20})
