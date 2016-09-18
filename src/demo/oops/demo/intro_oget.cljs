(ns oops.demo.intro-oget
  (:require-macros [oops.demo.logging :refer [log info]])
  (:require [oops.demo.main :refer [boot!]]
            [oops.core :refer [oget oset! ocall oapply ocall! oapply!
                               oget+ oset!+ ocall+ oapply+ ocall!+ oapply!+]]))

(boot! "/src/demo/oops/demo/intro_oget.cljs")

; --- MEAT STARTS HERE -->
; note: (log ...) expands to (.log js/console ...)

(def o #js {"k1" "v1"
            "k2" #js {"k21" "v21"}
            "k3" #js {"k31" #js {"k311" "v311"}
                      "k32" nil}
            "k4" ["cljs" "vector"]})

(log (oget o "k1"))                                                                                                           ; basic form works like `aget`
; => "v1"

(log (oget o "k2.k21"))                                                                                                       ; but selectors may use dot notation
; => "v21"

(log (oget o "k2.miss"))                                                                                                      ; access a missing key
; ERROR: Oops, Missing expected object key 'miss' on key path 'k2.miss'
; => null

(log (oget o "k3.k31.?maybe"))                                                                                                ; for potentially missing keys use "soft" access
; => null

(log (oget o "k4"))
; => ["cljs" "vector"]

(log (oget o "k4.something"))                                                                                                 ; warns you when accessing a non-js-object
; ERROR: Oops, Unexpected object value (cljs instance) on key path 'k4'
; => null

(log (= (oget o "k3.?k31.k311")                                                                                               ; instead of dot notation you may use (nested) collections
        (oget o "k3" "?k31" "k311")
        (oget o ["k3" "?k31" "k311"])
        (oget o [["k3"] "?k31"] "k311")))
; => true

(log (oget o (identity "k1")))                                                                                                ; unexpected dynamic selector
; compiler warning: Oops, Unexpected dynamic selector usage
; => "v1"

(log (oget+ o (identity "k1")))                                                                                               ; expected dynamic selector, no warnigns
; => "v1"

; <-- MEAT STOPS HERE ---
