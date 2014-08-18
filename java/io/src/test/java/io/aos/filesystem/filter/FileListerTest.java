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
package io.aos.filesystem.filter;

import io.aos.endpoint.file.bio.BioViewerTest;

import java.awt.Button;
import java.awt.Event;
import java.awt.Frame;
import java.awt.List;
import java.awt.Panel;
import java.awt.TextField;
import java.io.File;
import java.io.FilenameFilter;
import java.io.IOException;

public class FileListerTest extends Frame {
    private static final long serialVersionUID = -4631453454644797415L;
    private List list;
    private TextField infoarea;
    private Panel buttons;
    private Button parent, quit;
    private FilenameFilter filter;
    private File cwd;
    private String[] entries;

    // Parse command line arguments and create the FileLister object.
    // If an extension is specified, create a FilenameFilter for it.
    // If no directory is specified, use the current directory.
    public static void main(String... args) throws IOException {
        FileListerTest f;
        FilenameFilter filter = null;
        String directory = null;

        for (int i = 0; i < args.length; i++) {
            if (args[i].equals("-e")) {
                i++;
                if (i >= args.length)
                    usage();
                filter = new EndsWithFilter(args[i]);
            }
            else {
                if (directory != null)
                    usage(); // Already set
                else
                    directory = args[i];
            }
        }

        // if no directory specified, use the current directoy
        if (directory == null)
            directory = System.getProperty("user.dir");
        // Create the FileLister object
        f = new FileListerTest(directory, filter);
    }

    // Create the graphical user interface, and list the initial directory.
    public FileListerTest(String directory, FilenameFilter filter) throws IOException {
        super("File Lister");
        this.filter = filter;
        list = new List(12, false);
        infoarea = new TextField();
        infoarea.setEditable(false);
        buttons = new Panel();
        parent = new Button("Up a Directory");
        quit = new Button("Quit");
        buttons.add(parent);
        buttons.add(quit);
        this.add("Center", list);
        this.add("South", infoarea);
        this.add("North", buttons);
        this.resize(550, 350);
        this.show();

        // list the initial directory.
        list_directory(directory);
    }

    // This method uses the list() method to get all entries in a directory
    // and then displays them in the List component.
    public void list_directory(String directory) throws IOException {
        File dir = new File(directory);

        if (!dir.isDirectory())
            throw new IllegalArgumentException("FileLister: no such directory");
        list.clear();
        cwd = dir;
        this.setTitle(directory);

        entries = cwd.list(filter);
        for (int i = 0; i < entries.length; i++)
            list.addItem(entries[i]);
    }

    // This method uses various File methods to obtain information about
    // a file or directory. Then it displays that info in a TextField.
    public void show_info(String filename) throws IOException {
        File f = new File(cwd, filename);
        String info;

        if (!f.exists())
            throw new IllegalArgumentException("FileLister.show_info(): " + "no such file or directory");

        if (f.isDirectory())
            info = "Directory: ";
        else
            info = "File: ";

        info += filename + "    ";

        info += (f.canRead() ? "read   " : "       ") + (f.canWrite() ? "write   " : "        ") + f.length() + "   "
                + new java.util.Date(f.lastModified());

        infoarea.setText(info);
    }

    // This method handles the buttons and list events.
    public boolean handleEvent(Event e) {
        if (e.target == quit)
            System.exit(0);
        else if (e.target == parent) {
            String parent = cwd.getParent();
            if (parent == null)
                parent = "/"; // Bug workaround
            try {
                list_directory(parent);
            }
            catch (IllegalArgumentException ex) {
                infoarea.setText("Already at top");
            }
            catch (IOException ex) {
                infoarea.setText("I/O Error");
            }
            return true;
        }
        else if (e.target == list) {
            // when an item is selected, show its info.
            if (e.id == Event.LIST_SELECT) {
                try {
                    show_info(entries[((Integer) e.arg).intValue()]);
                }
                catch (IOException ex) {
                    infoarea.setText("I/O Error");
                }
            }
            // When the user double-clicks, change to the selected directory
            // or display the selected file.
            else if (e.id == Event.ACTION_EVENT) {
                try {
                    String item = new File(cwd, (String) e.arg).getAbsolutePath();
                    try {
                        list_directory(item);
                    }
                    catch (IllegalArgumentException ex) {
                        new BioViewerTest(item);
                    }
                }
                catch (IOException ex) {
                    infoarea.setText("I/O Error");
                }
            }
            return true;
        }
        return super.handleEvent(e);
    }

    public static void usage() {
        System.out.println("Usage: java FileLister [directory_name] " + "[-e file_extension]");
        System.exit(0);
    }
}

// This class is a simple FilenameFilter. It defines the required accept()
// method to determine whether a specified file should be listed. A file
// will be listed if its name ends with the specified extension, or if
// it is a directory.
class EndsWithFilter implements FilenameFilter {
    private String extension;

    public EndsWithFilter(String extension) {
        this.extension = extension;
    }

    public boolean accept(File dir, String name) {
        if (name.endsWith(extension))
            return true;
        else
            return (new File(dir, name)).isDirectory();
    }
}
