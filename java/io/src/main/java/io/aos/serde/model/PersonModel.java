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
package io.aos.serde.model;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InvalidObjectException;
import java.io.ObjectInputStream;
import java.io.ObjectInputValidation;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

public class PersonModel implements Serializable, ObjectInputValidation {
    private static final long serialVersionUID = 1L;

    static Map<String, String> thePeople = new HashMap<String, String>();

    private String name;
    private String ss;

    public PersonModel(String name, String ss) {
        this.name = name;
        this.ss = ss;
        thePeople.put(ss, name);
    }

    public static void main(String... args) throws IOException, ClassNotFoundException {

        PersonModel p1 = new PersonModel("Rusty", "123-45-5678");
        PersonModel p2 = new PersonModel("Beth", "321-45-5678");
        PersonModel p3 = new PersonModel("David", "453-45-5678");
        PersonModel p4 = new PersonModel("David", "453-45-5678");

        Iterator iterator = thePeople.values().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream oout = new ObjectOutputStream(bout);
        oout.writeObject(p1);
        oout.writeObject(p2);
        oout.writeObject(p3);
        oout.writeObject(p4);
        oout.flush();
        oout.close();

        ByteArrayInputStream binputStream= new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream oinputStream= new ObjectInputStream(binputStream);
        try {
            System.out.println(oinputStream.readObject());
            System.out.println(oinputStream.readObject());
            System.out.println(oinputStream.readObject());
            System.out.println(oinputStream.readObject());
        } catch (InvalidObjectException ex) {
            System.err.println(ex);
        }
        oinputStream.close();

        // now empty the map and try again
        thePeople.clear();
        binputStream= new ByteArrayInputStream(bout.toByteArray());
        oinputStream= new ObjectInputStream(binputStream);
        try {
            System.out.println(oinputStream.readObject());
            System.out.println(oinputStream.readObject());
            System.out.println(oinputStream.readObject());
            System.out.println(oinputStream.readObject());
        } catch (InvalidObjectException ex) {
            System.err.println(ex);
        }
        oinputStream.close();

        iterator = thePeople.values().iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }

    }

    private void readObject(ObjectInputStream inputStream) throws IOException, ClassNotFoundException {
        inputStream.registerValidation(this, 5);
        inputStream.defaultReadObject();
    }

    public void validateObject() throws InvalidObjectException {
        if (thePeople.containsKey(this.ss)) {
            throw new InvalidObjectException(this.name + " already exists");
        } else {
            thePeople.put(this.ss, this.name);
        }
    }

    public String toString() {
        return this.name + "\t" + this.ss;
    }

}
