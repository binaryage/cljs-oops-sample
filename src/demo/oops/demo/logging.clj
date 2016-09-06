(ns oops.demo.logging)

(defmacro log [& args]
  `(.log js/console ~@args))

(defmacro info [& args]
  `(.info js/console ~@args))
