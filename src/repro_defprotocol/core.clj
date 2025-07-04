(ns repro-defprotocol.core
  (:require [schema.core :as s])
  (:gen-class))

(s/defprotocol MyProtocolWithSchema
  "Docstring"
  :extend-via-metadata true
  (^:always-validate method1 :- s/Int
    [this a :- s/Bool]
    [this a :- s/Any b :- s/Str]
    "Method doc2")
  (^:never-validate method2 :- s/Int
    [this]
    "Method doc2"))

(s/defrecord RecordSchema []
  MyProtocolWithSchema
  (method1
    [_ a b]
    (let [_ [a b]]
      "doing cool stuff"))
  (method2
    [_ a b]
    (let [_ [a b]]
      "doing cool stuff")))

(defprotocol MyProtocol
  "Docstring"
  (method1
    [this a]
    [this a b]
    "Method doc2")
  (method2
    [this]
    "Method doc2"))

(defrecord MyRecord []
  MyProtocol
  (method1
    [_ a b]
    (let [_ [a b]]
      "doing cool stuff"))
  (method2 [_]
    "doing cool stuff"))

(defn -main
  "I don't do a whole lot ... yet."
  [& args]
  (.RecordSchema)
  (println "Hello, World!"))

  ;; clojure -M:clj-kondo/dev --lint - <<< "(require '[schema.core :as s])(s/defprotocol MyProtocolWithSchema  \"Docstring\"  :extend-via-metadata true  (^:always-validate method1 :- s/Int    [this a :- s/Bool]    [this a :- s/Any, b :- s/Str]    \"Method doc2\")  (^:never-validate method2 :- s/Int    [this]    \"Method doc2\"))(s/defrecord RecordSchema []  MyProtocolWithSchema  (do-stuff :- (s/pred map?)    [_ a :- s/Num b :- s/Str]    (let [_ [a b]]      \"doing cool stuff\")))"
