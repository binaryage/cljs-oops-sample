(defproject binaryage/oops-sample "0.1.0-SNAPSHOT"
  :description "An example integration of cljs-oops"
  :url "https://github.com/binaryage/cljs-oops-sample"

  :dependencies [[org.clojure/clojure "1.9.0-alpha12"]
                 ;[org.clojure/clojure "1.8.0"]
                 ;[clojure-future-spec "1.9.0-alpha12"]
                 [org.clojure/clojurescript "1.9.229"]
                 [org.clojure/core.async "0.2.391"]
                 [binaryage/oops "0.3.0"]
                 [binaryage/devtools "0.8.2"]
                 [com.cognitect/transit-clj "0.8.288"]
                 [cljs-http "0.1.41"]
                 [environ "1.1.0"]
                 [figwheel "0.5.7"]]

  :plugins [[lein-cljsbuild "1.1.4"]
            [lein-figwheel "0.5.7"]
            [lein-shell "0.5.0"]
            [lein-environ "1.1.0"]]

  ; =========================================================================================================================

  :source-paths ["src/demo"]

  :clean-targets ^{:protect false} ["resources/public/_compiled"
                                    "target"]

  :checkout-deps-shares ^:replace []                                                                                          ; http://jakemccrary.com/blog/2015/03/24/advanced-leiningen-checkouts-configuring-what-ends-up-on-your-classpath/

  ; =========================================================================================================================

  :cljsbuild {:builds {}}                                                                                                     ; prevent https://github.com/emezeske/lein-cljsbuild/issues/413

  :profiles {; --------------------------------------------------------------------------------------------------------------
             :demo
             {:cljsbuild {:builds {:demo
                                   {:source-paths ["src/demo"]
                                    :compiler     {:output-to       "resources/public/_compiled/demo/main.js"
                                                   :output-dir      "resources/public/_compiled/demo"
                                                   :asset-path      "_compiled/demo"
                                                   :main            oops.demo.main
                                                   :external-config {:oops/config {:runtime-error-reporting   :console
                                                                                   :runtime-warning-reporting :console}}
                                                   :preloads        [devtools.preload]
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

  :aliases {"demo"     ["with-profile" "+demo,+figwheel"
                        "figwheel"]
            "dev-demo" ["with-profile" "+demo,+figwheel,+checkouts"
                        "figwheel"]
            "cljs"     ["with-profile" "+demo"
                        "cljsbuild" "auto"]
            "present"  ["with-profile" "+demo"
                        "do"
                        "clean,"
                        "cljsbuild" "once,"
                        "shell" "scripts/dev-server.sh"]})
