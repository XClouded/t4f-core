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
package io.aos.crypto.seal;

import java.awt.Point;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.security.GeneralSecurityException;

import javax.crypto.Cipher;
import javax.crypto.SealedObject;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.junit.Test;

public class SealedPoint {

    public void seal() throws GeneralSecurityException, IOException {

        Point tdp = new Point(32, 45);
        FileOutputStream fout = new FileOutputStream("./target/point.des");
        ObjectOutputStream oout = new ObjectOutputStream(fout);

        // Create a key.
        byte[] desKeyData = { (byte) 0x90, (byte) 0x67, (byte) 0x3E,
                (byte) 0xE6, (byte) 0x42, (byte) 0x15, (byte) 0x7A, (byte) 0xA3 };
        DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = keyFactory.generateSecret(desKeySpec);

        // Use Data Encryption Standard.
        Cipher des = Cipher.getInstance("DES/ECB/PKCS5Padding");
        des.init(Cipher.ENCRYPT_MODE, desKey);

        SealedObject so = new SealedObject(tdp, des);
        oout.writeObject(so);
        oout.close();
        
    }

    public void unseal() throws IOException, GeneralSecurityException, ClassNotFoundException {

        FileInputStream fin = new FileInputStream("point.des");
        ObjectInputStream oin = new ObjectInputStream(fin);

        // Create a key.
        byte[] desKeyData = { (byte) 0x90, (byte) 0x67, (byte) 0x3E, (byte) 0xE6, (byte) 0x42, (byte) 0x15,
                (byte) 0x7A, (byte) 0xA3 };
        DESKeySpec desKeySpec = new DESKeySpec(desKeyData);
        SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
        SecretKey desKey = keyFactory.generateSecret(desKeySpec);

        SealedObject so = (SealedObject) oin.readObject();

        Point p = (Point) so.getObject(desKey);
        System.out.println(p);
        oin.close();

    }

}
