;****************************************************************
; * Licensed to the AOS Community (AOS) under one or more        *
; * contributor license agreements.  See the NOTICE file         *
; * distributed with this work for additional information        *
; * regarding copyright ownership.  The AOS licenses this file   *
; * to you under the Apache License, Version 2.0 (the            *
; * "License"); you may not use this file except in compliance   *
; * with the License.  You may obtain a copy of the License at   *
; *                                                              *
; *   http://www.apache.org/licenses/LICENSE-2.0                 *
; *                                                              *
; * Unless required by applicable law or agreed to in writing,   *
; * software distributed under the License is distributed on an  *
; * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
; * KIND, either express or implied.  See the License for the    *
; * specific language governing permissions and limitations      *
; * under the License.                                           *
; ****************************************************************/
(ns io.aos.clj.tiny
  (:gen-class
    :name com.domain.tiny
    :methods [#^{:static true} [binomial [int int] double]]))

(defn binomial
  "Calculate the binomial coefficient."
  [n k]
  (let [a (inc n)]
    (loop [b 1
           c 1]
      (if (> b k)
        c
        (recur (inc b) (* (/ (- a b) b) c))))))

(defn -binomial
  "A Java-callable wrapper around the 'binomial' function."
  [n k]
  (binomial n k))

(defn -main []
  (println (str "(binomial 5 3): " (binomial 5 3)))
  (println (str "(binomial 10042 111): " (binomial 10042 111)))
)
