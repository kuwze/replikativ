(ns replikativ.crdt.simple-gset.impl
  (:require [replikativ.protocols :refer [POpBasedCRDT -downstream
                                          PExternalValues -missing-commits
                                          PPullOp -pull]]
            [replikativ.crdt.simple-gset.core :refer [downstream]]
            #?(:clj [superv.async :refer [go-try go-loop-try <?]])
            #?(:clj [clojure.core.async :as async
                    :refer [>! timeout chan put! pub sub unsub close!]]
               :cljs [cljs.core.async :as async
                      :refer [>! timeout chan put! pub sub unsub close!]]))
  #?(:cljs (:require-macros [superv.async :refer [go-try go-loop-try <?]])))

(extend-type replikativ.crdt.SimpleGSet
  POpBasedCRDT
  (-handshake [this S] (into {} this))
  (-downstream [this op] (downstream this op))
  PExternalValues
  (-missing-commits [this S store out fetched-ch op] (go-try S #{})))
