(ns edu.vu.c3.core )

(def orthonormal-basis
  "This is the orthonomal basis for the metric with metrics supplied.
  These are key value pairs. The keys are the basis vectors and the values are
  the value of the vector dotted with itself"
  {:e- -1 :e+ 1 :e1 1 :e2 1 :e3 1})



(def k (vec (keys orthonormal-basis)))
;; [:e- :e+ :e1 :e2 :e3]

(def k1 (into #{} (for [a k1] #{a})))
;; #{:e1 :e+ :e2 :e3 :e-}

(defn make-M [kx]
  (apply hash-set
    (for [a k, b kx
     :when (not (contains? b a))]
     (conj b a))))
(def k2 (make-M k1))
(def k3 (make-M k2))
(def k4 (make-M k3))
(def k5 (make-M k4))

(def cannonical-basis
  (-> #{:s}
    (into k1)
    (into k2)
    (into k3)
    (into k4)
    (into k5)))
