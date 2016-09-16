(ns oops.demo.index
  (:require-macros [oops.demo.logging :refer [log info]])
  (:require [oops.demo.main :refer [boot!]]
            [oops.core :refer [oget oget+ oset! ocall oapply ocall! oapply!]]))

(boot! "/src/demo/oops/demo/index.cljs")

; --- MEAT STARTS HERE -->
; note: (log ...) expands to (.log js/console ...)

(def o #js {"key" "val"
            :kk 42
            "nested" #js {:nested-key "nested val"}})

(log (oget o "key"))
(log (oget o :kk))
(log (oget o "nested" "nested-key"))
(log (oget o "nested" :nested-key))
(log (oget o ["nested" "nested-key"]))
(log (oget o ["nested"] [["nested-key"]]))

(log (oget o :missing-key))

; <-- MEAT STOPS HERE ---
