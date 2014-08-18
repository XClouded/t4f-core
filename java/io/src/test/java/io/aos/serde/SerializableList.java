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
package io.aos.serde;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.Externalizable;
import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.net.Socket;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class SerializableList extends ArrayList implements Externalizable {

    public static void main(String... args) throws Exception {
    
        SerializableList list = new SerializableList();
        list.add("Element 1");
        list.add(new Integer(9));
        list.add(new URL("http://www.oreilly.com/"));
    
        // not Serializable
        list.add(new Socket("www.oreilly.com", 80));
    
        list.add("Element 5");
        list.add(new Integer(9));
        list.add(new URL("http://www.oreilly.com/"));
    
        ByteArrayOutputStream bout = new ByteArrayOutputStream();
        ObjectOutputStream temp = new ObjectOutputStream(bout);
        temp.writeObject(list);
        temp.close();
    
        ByteArrayInputStream binputStream= new ByteArrayInputStream(bout.toByteArray());
        ObjectInputStream oinputStream= new ObjectInputStream(binputStream);
        List outputStream = (List) oinputStream.readObject();
        Iterator iterator = outputStream.iterator();
        while (iterator.hasNext()) {
            System.out.println(iterator.next());
        }
    
    }

    public void writeExternal(ObjectOutput outputStream) throws IOException {

        outputStream.writeInt(size());
        for (int i = 0; i < size(); i++) {
            if (get(i) instanceof Serializable) {
                outputStream.writeObject(get(i));
            }
            else {
                outputStream.writeObject(null);
            }
        }
    }

    public void readExternal(ObjectInput inputStream) throws IOException, ClassNotFoundException {

        int elementCount = inputStream.readInt();
        this.ensureCapacity(elementCount);
        for (int i = 0; i < elementCount; i++) {
            this.add(inputStream.readObject());
        }
    }

}
