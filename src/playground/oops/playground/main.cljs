(ns oops.playground.main
  (:require [oops.core :refer [oget]]))

; this should cause compilation error, see project.clj -> playground build -> :static-nil-target-object :error
(oget nil "k1")
