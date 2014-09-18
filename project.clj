(defproject booth-client "0.1.0-SNAPSHOT"
  :description "Simple client to the booth service"
  :url "http://github.com/scheibenkaes/booth-client"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.6.0"]
                 [org.clojure/core.async "0.1.338.0-5c5012-alpha"]
                 [org.clojure/tools.cli "0.3.1"]]
  :main ^:skip-aot booth-client.core
  :target-path "target/%s"
  :profiles {:uberjar {:aot :all}})
