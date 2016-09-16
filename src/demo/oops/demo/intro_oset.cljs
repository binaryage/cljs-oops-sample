(ns oops.demo.intro-oset
  (:require-macros [oops.demo.logging :refer [log info]])
  (:require [oops.demo.main :refer [boot!]]
            [oops.core :refer [oget oset! ocall oapply ocall! oapply!
                               oget+ oset!+ ocall+ oapply+ ocall!+ oapply!+]]))

(boot! "/src/demo/oops/demo/intro_oset.cljs")

; --- MEAT STARTS HERE -->
; note: (log ...) expands to (.log js/console ...)

(def o #js {"k1" "v1"
            "k2" #js {"k21" "v21"}})

(log (pr-str (oset! o "k3" "v3")))                                                                                            ; basic form works like `aset`
; => #js {:k1 "v1", :k2 #js {:k21 "v21"}, :k3 "v3"}

(log (pr-str (oset! o "k2.k21" "new")))                                                                                       ; but you can use dots!
; => #js {:k1 "v1", :k2 #js {:k21 "new"}, :k3 "v3"}

(log (pr-str (oset! o "k2.k22.k221" "v221")))                                                                                 ; you cannot create new keys by mistake
; ERROR: Oops, Missing expected object key 'k22' on key path 'k2.k22'                                                         ; you get an error
; => #js {:k1 "v1", :k2 #js {:k21 "new"}, :k3 "v3"}                                                                           ; and unmodified object back

(log (pr-str (oset! o "k2.!k22.!k221" "v221")))                                                                               ; you have to do explicit punching with !
; => #js {:k1 "v1", :k2 #js {:k21 "new", :k22 #js {:k221 "v221"}}, :k3 "v3"}

(log (= (pr-str (oset! (js-obj) "!k1.!k2.!k3" "v"))                                                                           ; instead of dot notation you may use (nested) collections
        (pr-str (oset! (js-obj) "!k1" "!k2" "!k3" "v"))
        (pr-str (oset! (js-obj) ["!k1" ["!k2"] "!k3"] "v"))))
; => true


(log (pr-str (oset! (js-obj) (identity "k1") "v1")))                                                                          ; unexpected dynamic selector
; compiler warning: Oops, Unexpected dynamic property access
; => #js {:k1 "v1"}


(log (pr-str (oset!+ (js-obj) (identity "k1") "v1")))                                                                         ; expected dynamic selector, no warnigns
; => #js {:k1 "v1"}

; <-- MEAT STOPS HERE ---
