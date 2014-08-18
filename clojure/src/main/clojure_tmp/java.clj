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
(ns io.aos.clj.java)

(import 'java.util.Date)
(import [java.util Calendar TimeZone Date])
(import [java.util Date HashMap])

(def today (new Date))

(println today)

(let [today (Date.)]
(.getTime today))

(System/currentTimeMillis)

(defn utc-time [d]
	        (let [cal (Calendar/getInstance)]
	              (.setTimeZone cal (TimeZone/getTimeZone "UTC"))
	              (.setTime cal d)
	          cal))


(defn utc-time [d]        
	         (doto (Calendar/getInstance)
	              (.setTimeZone (TimeZone/getTimeZone "UTC"))
	              (.setTime d)))


(.length (.getProperty (System/getProperties) "user.country"))

(. (. (System/getProperties) getProperty "user.country") length)


(..
	       (System/getProperties)
	       (getProperty "user.country")
	       (length))

(set! *warn-on-reflection* true)

(defn str-length [s] (.length s))

(defn str-length [#^String s] (.length s))

(proxy [Runnable] []
	        (run []
	             (println "running ..."))
)

(reify Runnable
	             (run [this]
	                  (println "running ..."))
)
