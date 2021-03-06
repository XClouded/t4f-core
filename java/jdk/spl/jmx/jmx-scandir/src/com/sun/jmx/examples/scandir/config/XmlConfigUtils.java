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
/*
 * XmlConfigUtils.java
 *
 * Created on July 13, 2006, 3:52 PM
 *
 * @(#)XmlConfigUtils.java	1.3 10/03/23
 *
 * Copyright (c) 2006, Oracle and/or its affiliates. All rights reserved.
 *
 * Redistribution and use in source and binary forms, with or without
 * modification, are permitted provided that the following conditions are met:
 *
 * -Redistribution of source code must retain the above copyright notice, this
 *  list of conditions and the following disclaimer.
 *
 * -Redistribution in binary form must reproduce the above copyright notice,
 *  this list of conditions and the following disclaimer in the documentation
 *  and/or other materials provided with the distribution.
 *
 * Neither the name of Oracle or the names of contributors may
 * be used to endorse or promote products derived from this software without
 * specific prior written permission.
 *
 * This software is provided "AS IS," without a warranty of any kind. ALL
 * EXPRESS OR IMPLIED CONDITIONS, REPRESENTATIONS AND WARRANTIES, INCLUDING
 * ANY IMPLIED WARRANTY OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE
 * OR NON-INFRINGEMENT, ARE HEREBY EXCLUDED. SUN MICROSYSTEMS, INC. ("SUN")
 * AND ITS LICENSORS SHALL NOT BE LIABLE FOR ANY DAMAGES SUFFERED BY LICENSEE
 * AS A RESULT OF USING, MODIFYING OR DISTRIBUTING THIS SOFTWARE OR ITS
 * DERIVATIVES. IN NO EVENT WILL SUN OR ITS LICENSORS BE LIABLE FOR ANY LOST
 * REVENUE, PROFIT OR DATA, OR FOR DIRECT, INDIRECT, SPECIAL, CONSEQUENTIAL,
 * INCIDENTAL OR PUNITIVE DAMAGES, HOWEVER CAUSED AND REGARDLESS OF THE THEORY
 * OF LIABILITY, ARISING OUT OF THE USE OF OR INABILITY TO USE THIS SOFTWARE,
 * EVEN IF SUN HAS BEEN ADVISED OF THE POSSIBILITY OF SUCH DAMAGES.
 *
 * You acknowledge that this software is not designed, licensed or intended
 * for use in the design, construction, operation or maintenance of any
 * nuclear facility.
 */

package com.sun.jmx.examples.scandir.config;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Logger;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.bind.Unmarshaller;

/**
 * The class XmlConfigUtils is used to deal with XML serialization
 * and XML files. 
 * 
 * @author Sun Microsystems, 2006 - All rights reserved.
 */
public class XmlConfigUtils {
    
    /**
     * A URI for our XML configuration namespace. This doesn't start with
     * http:// because we are not going to publish this private schema
     * anywhere.
     **/
    public static final String NAMESPACE =
            "jmx:com.sun.jmx.examples.scandir.config";
    /**
     * A LOGGER for this class.
     **/
    private static final Logger LOGGER=
            LoggerFactory.getLogger(XmlConfigUtils.class.getName());

    // Our JAXBContext.
    private static JAXBContext context;
    
    // The file name of the XML file in which an instance of this object
    // will read and write XML data.
    final String file;
    
    /**
     * Creates a new instance of XmlConfigUtils.
     * @param file The file name of the XML file in which an instance of this 
     *        object will read and write XML data.
     */
    public XmlConfigUtils(String file) {
        this.file = file;
    }

    /**
     * Write the given bean to the XML file.
     * <p>
     * Performs an atomic write, first writing in {@code <file>.new}, then
     * renaming {@code <file>} to {@code <file>~}, then renaming 
     * renaming {@code <file>.new} to {@code <file>}.
     * </p>
     * @param bean The configuration to write in the XML file.
     * @throws IOException if write to file failed.
     **/
    public synchronized void writeToFile(ScanManagerConfig bean) 
        throws IOException {
        
        // Creates a new file named <file>.new
        final File f = newXmlTmpFile(file);
        try {
            final FileOutputStream out = new FileOutputStream(f);
            boolean failed = true;
            try {
                // writes to <file>.new
                write(bean,out,false);
                
                // no exception: set failed=false for finaly {} block.
                failed = false;
            } finally {
                out.close();
                // An exception was raised: delete temporary file.
                if (failed == true) f.delete();
            }
            
            // rename <file> to <file>~ and <file>.new to <file>
            commit(file,f);
        } catch (JAXBException x) {
            final IOException io =
                    new IOException("Failed to write SessionConfigBean to " +
                    file+": "+x,x);
            throw io;
        }
    }
    
    /**
     * Creates an XML string representation of the given bean.
     * @throws IllegalArgumentException if the bean class is not known by the
     *         underlying XMLbinding context. 
     * @return An XML string representation of the given bean.
     **/
    public static String toString(Object bean) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final Marshaller m = createMarshaller();
            m.setProperty(m.JAXB_FRAGMENT,Boolean.TRUE);
            m.marshal(bean, baos);
            return baos.toString();
        } catch (JAXBException x) {
            final IllegalArgumentException iae = 
                    new IllegalArgumentException(
                        "Failed to write SessionConfigBean: "+x,x);
            throw iae;
        }
    }
    
    /**
     * Creates an XML clone of the given bean.
     * <p>
     * In other words, this method XML-serializes the given bean, and 
     * XML-deserializes a copy of that bean.
     * </p>
     * @return A deep-clone of the given bean.
     * @throws IllegalArgumentException if the bean class is not known by the
     *         underlying XML binding context.
     * @param bean The bean to clone.
     */
    public static ScanManagerConfig xmlClone(ScanManagerConfig bean) {
        final Object clone = copy(bean);
        return (ScanManagerConfig)clone;
    }
    
    /**
     * Creates an XML clone of the given bean.
     * <p>
     * In other words, this method XML-serializes the given bean, and 
     * XML-deserializes a copy of that bean.
     * </p>
     * @throws IllegalArgumentException if the bean class is not known by the
     *         underlying XML binding context.
     * @return A deep-clone of the given bean.
     **/
    private static Object copy(Object bean) {
        try {
            final ByteArrayOutputStream baos = new ByteArrayOutputStream();
            final Marshaller m = createMarshaller();
            m.marshal(bean, baos);
            final ByteArrayInputStream bais = 
                    new ByteArrayInputStream(baos.toByteArray());
            return createUnmarshaller().unmarshal(bais);
        } catch (JAXBException x) {
            final IllegalArgumentException iae = 
                    new IllegalArgumentException("Failed to write SessionConfigBean: "+x,x);
            throw iae;
        }
    }
   
    /**
     * Creates an XML clone of the given bean.
     * <p>
     * In other words, this method XML-serializes the given bean, and 
     * XML-deserializes a copy of that bean.
     * </p>
     * @return A deep-clone of the given bean.
     * @throws IllegalArgumentException if the bean class is not known by the
     *         underlying XML binding context.
     * @param bean The bean to clone.
     */
    public static DirectoryScannerConfig xmlClone(DirectoryScannerConfig bean) {
        final Object clone = copy(bean);
        return (DirectoryScannerConfig)clone;
    }

    /**
     * Reads the configuration from the XML configuration file.
     * @throws IOException if it fails to read the configuration.
     * @return A {@code ScanManagerConfig} bean read from the 
     *         XML configuration file.
     **/
    public synchronized ScanManagerConfig readFromFile() throws IOException {
        final File f = new File(file);
        if (!f.exists())
            throw new IOException("No such file: "+file);            
        if (!f.canRead()) 
            throw new IOException("Can't read file: "+file);
        try {
            return read(f);
        } catch (JAXBException x) {
            final IOException io = 
                    new IOException("Failed to read SessionConfigBean from " +
                    file+": "+x,x);
            throw io;
        }
    }

    /**
     * Reads the configuration from the given XML configuration file.
     * @param f the file to read from.
     * @return A {@code ScanManagerConfig} bean read from the 
     *         XML configuration file.
     * @throws javax.xml.bind.JAXBException if it fails to read the configuration.
     */
    public static ScanManagerConfig read(File f)
        throws JAXBException {
        final Unmarshaller u = createUnmarshaller();
        return (ScanManagerConfig) u.unmarshal(f);
        
    }
        
    /**
     * Writes the given bean to the given output stream.
     * @param bean the bean to write.
     * @param os the output stream to write to.
     * @param fragment whether the {@code <?xml ... ?>} header should be
     *        included. The header is not included if the bean is just an
     *        XML fragment encapsulated in a higher level XML element.
     * @throws JAXBException An XML Binding exception occurred. 
     **/
    public static void write(ScanManagerConfig bean, OutputStream os,
            boolean fragment)
        throws JAXBException {
        writeXml((Object)bean,os,fragment);
    }
    
    /**
     * Writes the given bean to the given output stream.
     * @param bean the bean to write.
     * @param os the output stream to write to.
     * @param fragment whether the {@code <?xml ... ?>} header should be
     *        included. The header is not included if the bean is just an
     *        XML fragment encapsulated in a higher level XML element.
     * @throws JAXBException An XML Binding exception occurred. 
     **/
    public static void write(ResultRecord bean, OutputStream os, boolean fragment)
        throws JAXBException {
        writeXml((Object)bean,os,fragment);
    }
    
    /**
     * Writes the given bean to the given output stream.
     * @param bean the bean to write.
     * @param os the output stream to write to.
     * @param fragment whether the {@code <?xml ... ?>} header should be
     *        included. The header is not included if the bean is just an
     *        XML fragment encapsulated in a higher level XML element.
     * @throws JAXBException An XML Binding exception occurred. 
     **/
    private static void writeXml(Object bean, OutputStream os, boolean fragment)
        throws JAXBException {
        final Marshaller m = createMarshaller();
        if (fragment) m.setProperty(m.JAXB_FRAGMENT,Boolean.TRUE);
        m.marshal(bean,os);
    }
    
    // Creates a JAXB Unmarshaller.
    private static Unmarshaller createUnmarshaller() throws JAXBException {
        return getContext().createUnmarshaller();
    }
    
    // Creates a JAXB Marshaller - for nicely XML formatted output.
    private static Marshaller createMarshaller() throws JAXBException {
        final  Marshaller m = getContext().createMarshaller();
        m.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT,Boolean.TRUE);
        return m;
    }
    
    // Creates a JAXBContext if needed, and returns it.
    // The JAXBContext instance we create will be able to handle the
    // ScanManagerConfig and ResultRecord classes, plus all the property
    // classes they reference (DirectoryScannerBean etc...).
    //
    private static synchronized JAXBContext getContext() throws JAXBException {
        if (context == null)
            context = JAXBContext.newInstance(ScanManagerConfig.class,
                                              ResultRecord.class);
        return context;
    }


    // Creates a new XML temporary file called <basename>.new
    // This method is used to implement atomic writing to file.
    // The usual sequence is:
    //
    // Final tmp = newXmlTmpFile(basename);
    // boolean failed = true;
    // try {
    //      ... write to 'tmp' ...
    //      // no exception: set failed=false for finaly {} block.
    //      failed = false;
    // } finally
    //      // failed==true means there was an exception and 
    //      // commit won't be called...
    //      if (failed==true) tmp.delete();
    // }
    // commit(tmp,basename)
    //
    private static File newXmlTmpFile(String basename) throws IOException {
        final File f = new File(basename+".new");
        if (!f.createNewFile())
            throw new IOException("file "+f.getName()+" already exists");
 
        try {
            final OutputStream newStream = new FileOutputStream(f);
            try {
                final String decl =
                    "<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>";
                newStream.write(decl.getBytes("UTF-8"));
                newStream.flush();
            } finally {
                newStream.close();
            }
        } catch (IOException x) {
            f.delete();
            throw x;
        }
        return f;
    }
    
    // Commit the temporary file by renaming <basename> to <baesname>~
    // and tmpFile to <basename>.
    private static File commit(String basename, File tmpFile)
        throws IOException {
        try {
            final String backupName = basename+"~";
            final File desired = new File(basename);
            final File backup = new File(backupName);
            backup.delete();
            if (desired.exists()) {
                if (!desired.renameTo(new File(backupName)))
                    throw new IOException("can't rename to "+backupName);
            }
            if (!tmpFile.renameTo(new File(basename))) 
                throw new IOException("can't rename to "+basename);
        } catch (IOException x) {
            tmpFile.delete();
            throw x;
        }
        return new File(basename);
    }
    
    /**
     * Creates a new committed XML file for {@code <basename>}, containing only
     * the {@code <?xml ...?>} header.
     * <p>This method will rename {@code <basename>} to {@code <basename>~}, 
     * if it exists.
     * </p>
     * @return A newly created XML file containing the regular 
     *         {@code <?xml ...?>} header.
     * @param basename The name of the new file. 
     * @throws IOException if the new XML file couldn't be created.
     */
    public static File createNewXmlFile(String basename) throws IOException {
        return commit(basename,newXmlTmpFile(basename));
    }

}
