/****************************************************************
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
 ****************************************************************/
package io.aos.clazz.inner;

/**
 * 
 */
interface IDrawable {
	void draw();
}

class Shape implements IDrawable {
	public void draw() {
		System.out.println("drawing a shape");
	}
}

class Painter {
	public Shape createCircle(final double radius) {
		class Circle extends Shape {

			public void draw() {
				System.out.println("drawing a circle");
			}
		}
		Circle c1 = new Circle();
		Circle c2 = new Circle();
		return new Circle();
	}
}

public class LocalInnerClassExample1 {
	public static void main(String... args) {
		Shape s = new Painter().createCircle(5);
		s.draw();
		System.out.println(s.getClass());
	}
}
