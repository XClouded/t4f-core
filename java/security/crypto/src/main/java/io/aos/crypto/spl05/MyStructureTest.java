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
package io.aos.crypto.spl05;


import io.aos.crypto.spl04.Utils;

import java.util.Date;


/**
 * Test for MyStructure
 */
public class MyStructureTest
{
    public static void main(String... args)
        throws Exception
    {
        byte[] baseData = new byte[5];
        Date   created = new Date(0); // 1/1/1970
        
        MyStructure	structure = new MyStructure(0, created, baseData, null, null);
        
        System.out.println(Utils.toHex(structure.getEncoded()));
        if (!structure.equals(structure.toASN1Object()))
        {
            System.out.println("comparison failed.");
        }
        
        structure = new MyStructure(0, created, baseData, "hello", null);
        
        System.out.println(Utils.toHex(structure.getEncoded()));
        if (!structure.equals(structure.toASN1Object()))
        {
            System.out.println("comparison failed.");
        }
        
        structure = new MyStructure(0, created, baseData, null, "world");
        
        System.out.println(Utils.toHex(structure.getEncoded()));
        if (!structure.equals(structure.toASN1Object()))
        {
            System.out.println("comparison failed.");
        }
        
        structure = new MyStructure(0, created, baseData, "hello", "world");
        
        System.out.println(Utils.toHex(structure.getEncoded()));
        if (!structure.equals(structure.toASN1Object()))
        {
            System.out.println("comparison failed.");
        }
        
        structure = new MyStructure(1, created, baseData, null, null);
        
        System.out.println(Utils.toHex(structure.getEncoded()));
        if (!structure.equals(structure.toASN1Object()))
        {
            System.out.println("comparison failed.");
        }
    }
}
