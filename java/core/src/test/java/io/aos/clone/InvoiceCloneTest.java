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
package io.aos.clone;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotSame;

import org.junit.Test;

public class InvoiceCloneTest {
    
    @Test
    public void testClone() throws CloneNotSupportedException {

        Invoice invoice = new Invoice("ref1", new InvoiceLine("product1", 1));
        
        Invoice invoiceClone = (Invoice) invoice.clone();
        assertEquals(invoiceClone.getReference(), invoice.getReference());
        assertEquals(invoiceClone.getInvoiceLines().size(), invoice.getInvoiceLines().size());
        
        invoiceClone.setReference("new reference");
        assertNotSame(invoiceClone.getReference(), invoice.getReference());
        
        invoice.getInvoiceLines().get(0).setPrice(2);
        assertNotSame(invoiceClone.getInvoiceLines().get(0).getPrice(), invoice.getInvoiceLines().get(0).getPrice());
    
    }

}
