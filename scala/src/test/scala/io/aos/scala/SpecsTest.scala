/**
 * **************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regarding copyright ownership.  The AOS licenses this file   *
 * to you under the Apache License, Version 2.0 (the            *
 * "License"); you may not use this file except in compliance   *
 * with the License.  You may obtain a copy of the License at   *
 *                                                              *
 *   http://www.apache.org/licenses/LICENSE-2.0                 *
 *                                                              *
 * Unless required by applicable law or agreed to in writing,   *
 * software distributed under the License is distributed on an  *
 * "AS IS" BASIS, WITHOUT WARRANTIES OR CONDITIONS OF ANY       *
 * KIND, either express or implied.  See the License for the    *
 * specific language governing permissions and limitations      *
 * under the License.                                           *
 * **************************************************************
 */
package io.aos.scala

import org.junit.runner.RunWith
import org.specs._
import org.specs.matcher._
import org.specs.runner.{ JUnitSuiteRunner, JUnit }

/**
 * Sample specification.
 *
 * This specification can be executed with: scala -cp <your classpath=""> ${package}.SpecsTest
 * Or using maven: mvn test
 *
 * For more information on how to write or run specifications, please visit: http://code.google.com/p/specs.
 *
 */
@RunWith(classOf[JUnitSuiteRunner])
class SpecsTest extends Specification with JUnit /*with ScalaCheck*/ {

  "My" should {
    "allow " in {

      //0
    }
    "deny " in {
      //0
    }
  }

  "A List" should {
    "have a size method returning the number of elements in the list" in {
      List(1, 2, 3).size must_== 3
    }
    // add more examples here
    // ...
  }

}

object MySpecMain {
  def main(args: Array[String]) {
    new SpecsTest().main(args)
  }

}
