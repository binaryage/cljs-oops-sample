(defproject binaryage/oops-sample "0.1.0-SNAPSHOT"
  :description "An example integration of cljs-oops"
  :url "https://github.com/binaryage/cljs-oops-sample"

  :dependencies [[org.clojure/clojure "1.9.0"]
                 [org.clojure/clojurescript "1.10.439"]
                 [org.clojure/core.async "0.4.490"]
                 [binaryage/oops "0.6.4"]
                 [binaryage/devtools "0.9.10"]
                 [com.cognitect/transit-clj "0.8.313"]
                 [cljs-http "0.1.45"]
                 [environ "1.1.0"]
                 [figwheel "0.5.17"]]

  :plugins [[lein-cljsbuild "1.1.7"]
            [lein-figwheel "0.5.17"]
            [lein-shell "0.5.0"]
            [lein-environ "1.1.0"]]

  ; =========================================================================================================================

  ; reset :base profile
  :test-paths ^:replace []
  :checkout-deps-shares ^:replace []                                                                                          ; http://jakemccrary.com/blog/2015/03/24/advanced-leiningen-checkouts-configuring-what-ends-up-on-your-classpath/

  ; for Cursive
  :source-paths ["src/demo"]
  :resource-paths ^:replace ["resources"
                             "scripts"]

  :clean-targets ^{:protect false} ["resources/public/.compiled"
                                    "target"]


  ; =========================================================================================================================

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {; --------------------------------------------------------------------------------------------------------------
             :demo
             {:cljsbuild {:builds {:demo
                                   {:source-paths ["src/demo"]
                                    :compiler     {:output-to       "resources/public/.compiled/demo/main.js"
                                                   :output-dir      "resources/public/.compiled/demo"
                                                   :asset-path      ".compiled/demo"
                                                   :main            oops.demo.main
                                                   :external-config {:oops/config {:runtime-error-reporting   :console
                                                                                   :runtime-warning-reporting :console}}
                                                   :preloads        [devtools.preload]
                                                   :checked-arrays  :warn                                                     ; see https://github.com/binaryage/cljs-oops/issues/14
                                                   :optimizations   :none}}}}}
             ; --------------------------------------------------------------------------------------------------------------
             :checkouts
             {:checkout-deps-shares ^:replace [:source-paths
                                               :test-paths
                                               :resource-paths
                                               :compile-path
                                               #=(eval leiningen.core.classpath/checkout-deps-paths)]
              :cljsbuild            {:builds {:demo
                                              {:source-paths ["checkouts/cljs-devtools/src/lib"
                                                              "checkouts/cljs-oops/src/lib"]}}}}
             ; --------------------------------------------------------------------------------------------------------------
             :figwheel
             {:figwheel  {:server-port    7330
                          :server-logfile ".figwheel/server.log"}
              :cljsbuild {:builds {:demo
                                   {:figwheel true}}}}}


  ; =========================================================================================================================

  :aliases {"demo"     ["with-profile" "+demo,+figwheel" "figwheel"]
            "dev-demo" ["with-profile" "+demo,+figwheel,+checkouts" "figwheel"]
            "cljs"     ["with-profile" "+demo" "cljsbuild" "auto"]
            "present"  ["shell" "scripts/present.sh"]})
