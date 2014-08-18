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
package io.aos.primitive;

public class PrimitiveAssignment {
    
    @SuppressWarnings(value="")
    public static void main(String... args) {
        int x, y; // Declare an integer
        char c; // Declare a char
        String str;  // Declar a String
        float z = 3.141592f; // Declare and assign a float
        double w = 3.141592; // Declare and assign a double
        boolean thruth = true; // Declare and assign a boolean
        
        str = "My string"; // Assign the string
        // Assignation d'une valeur � une varaible char
        c = 'a';
        
        // Assignation d'entier
        x = 2;
        y = 5000;
        
        // Mauvais exemples:
        
        // Erreur de troncature
        // y=3.141592;
        
        // La virgule n'est pas utilis�e pour les nombres
        // w=175,00;
        
        // Impossible d'assigner un nombre � un boolean
        // thruth = 1;
        
        // Demande un casting z=float et pas double
        // z=3.141592; 
        
    }
}
