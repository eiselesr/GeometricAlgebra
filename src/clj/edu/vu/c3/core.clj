(ns edu.vu.c3.core )

(def orthonormal-basis
  "This is the orthonomal basis for the metric with metrics supplied.
  These are key value pairs. The keys are the basis vectors and the values are
  the value of the vector dotted with itself"
  {:e- -1 :e+ 1 :e1 1 :e2 1 :e3 1})



(def k (vec (keys orthonormal-basis)))
;; [:e- :e+ :e1 :e2 :e3]

(def k1 (into #{} (for [a k] #{a})))
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
  (-> [#{}]
    ;; the empty collection represents a scalar
    (into k1)
    (into k2)
    (into k3)
    (into k4)
    (into k5)))

(defn sort-blade [bl]
  (vec (sort-by #(.indexOf k %) bl)))

(def cb (mapv sort-blade cannonical-basis))
(def mtkey (for [a cb b cb] [a b]))


(defn measured-merge-sort
  "perform merge sort on two lists keeping
  track of were things move to in the new list"
  [coll1 coll2]
  ;; (println "coll1: " coll1 ", coll2: " coll2)
  (loop [nu [], flips 0, elims 0,
          c1 coll1, c2 coll2]
    (cond
      (every? empty? [c1 c2])
        [nu flips elims]
      (empty? c1)
        [(into nu c2) flips elims]
      (empty? c2)
        [(into nu c1) flips elims]
      :else
        (let [a1 (first c1), ix1 (.indexOf k a1),
              a2 (first c2), ix2 (.indexOf k a2)]
          (cond
            (= ix1 ix2)
                (recur nu flips (inc elims)
                  (rest c1) (rest c2) )

            (< ix1 ix2)
                (recur (conj nu a1) flips elims
                  (rest c1) c2)

            :else
                (recur (conj nu a2) (+ flips (count c1)) elims
                  c1 (rest c2)))))))

(defn make-value-entry [[coll1 coll2]]
  (let [[key flip-count eliminations]
            (measured-merge-sort coll1 coll2)
        coef (if (odd? flip-count) -1 +1)]
    (if (pos? eliminations)
      [[coef key] []]
      [[] [coef key]] )))

(def multiplication-table
  (into {} (map (fn [key] [key (make-value-entry key)]) mtkey)))
