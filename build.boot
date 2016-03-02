(set-env!
  :resource-paths #{"src/clj"}
  ;clojure checks src by default but we changed the src path so we have to tell it.
  :source-paths #{"src/clj"}
  :dependencies '[[me.raynes/conch "0.8.0"]])


(task-options!
  pom {:project 'geometric-algebra
       :version "0.1.0"}
  jar {:manifest {"Foo" "bar"}})

(require '[edu.vu.c3.core :as c3])
