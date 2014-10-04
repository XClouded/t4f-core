/****************************************************************
 * Licensed to the AOS Community (AOS) under one or more        *
 * contributor license agreements.  See the NOTICE file         *
 * distributed with this work for additional information        *
 * regardataInputStreamg copyright ownership.  The AOS licenses this file   *
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
package io.aos.in.bio.typed;

import java.io.DataInputStream;
import java.io.IOException;

/**
 * The use of DataInputStream here is a little forced. It would be more natural
 * (though more complicated) to read the bytes and manually convert them to an
 * int.
 */
public abstract class AbstractDataIn extends AbstractTypedIn {
    protected DataInputStream dataInputStream;

    public AbstractDataIn(DataInputStream dataInputStream) {
        super(dataInputStream);
        this.dataInputStream = dataInputStream;
    }

    @Override
    public int available() throws IOException {
        return (buf.length - index) + in.available();
    }

}
