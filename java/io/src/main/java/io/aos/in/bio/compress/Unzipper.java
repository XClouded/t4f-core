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
package io.aos.in.bio.compress;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Calendar;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

public class Unzipper {
    private static String inputFileZIP;
    private static String outputFileZIP;
    private static Vector liste = new Vector();
    private static final char separ = File.separatorChar;
    public static final int placeNom = 0;
    public static final int placeTaille = 1;
    public static final int placeCompression = 2;
    public static final int placeDate = 3;
    public static final int placeCommentaire = 4;

    public static void main(String... args) throws Exception {

        for (int i = 68; i < 100; i++) {
            String filenameZIP = "./downloaded-skins/0" + i + ".zip";
            File file = new File(filenameZIP);
            if (!file.exists()) {
                return;
            }
            file = null;
            String pathOut = "./downloaded-skins/";
            InputStream inputStream = null;
            try {
                inputStream = new BufferedInputStream(new FileInputStream(filenameZIP));
            }
            catch (FileNotFoundException e) {
                e.printStackTrace();
            }

            ZipInputStream zinputStream = new ZipInputStream(inputStream);
            ZipEntry e;

            Unzipper zipUtil = new Unzipper();
            try {
                while ((e = zinputStream.getNextEntry()) != null) {
                    zipUtil.unzip(zinputStream, e, pathOut);
                }
                zinputStream.close();
            }
            catch (IOException e1) {
                e1.printStackTrace();
            }

        }
    }

    public void unzip(ZipInputStream zin, ZipEntry e, String pathOut) throws IOException {

        createDirPathDisk(pathOut + e.getName());

        if (e.isDirectory()) {
            File f = new File(pathOut + e.getName());
            f.mkdirs();
        }

        else {

            FileOutputStream outputStream = new FileOutputStream(pathOut + e.getName());
            byte[] b = new byte[512];
            int len = 0;
            while ((len = zin.read(b)) != -1) {
                outputStream.write(b, 0, len);
            }
            outputStream.close();
        }

    }

    public void createDirPathDisk(String dirName) {
        String filePath = "";
        if (dirName.contains("/")) {
            filePath = dirName.substring(0, dirName.lastIndexOf("/"));
        }
        else if (dirName.contains("\\")) {
            filePath = dirName.substring(0, dirName.lastIndexOf("\\"));
        }
        if (filePath.length() > 0) {
            createDir(filePath);
        }
    }

    /**
     * Create a directory
     * 
     * @param dirname
     *            : complete name of directory check if exists before
     */
    public void createDir(String dirname) {
        File file = new File(dirname);
        if (!file.exists()) {
            file.mkdirs();
        }
    }

    /**
     * Cr�e le zip
     * 
     * @param inputfichier
     *            Le nom du inputFileZIP zip, avec son cheminputStreamcomplet
     */
    public static boolean filenameToUnzip(String inputfichier) {
        boolean fileexsists = false;
        Unzipper.inputFileZIP = inputfichier;
        File f = new File(inputFileZIP);
        // Si le inputFileZIP existe, on r�cup�re le nom et les attributs des
        // fichiers compr�ss�s dans le zip
        if (f.exists()) {
            // C'est un inputFileZIP zip
            ZipFile fic = null;

            try {
                fic = new ZipFile(inputFileZIP);
            }
            catch (IOException e) {
                fileexsists = true;
            }

            if (fileexsists) {

                Enumeration enumeration = fic.entries();

                while (enumeration.hasMoreElements())

                    f = (File) enumeration.nextElement();

                System.out.println(f.getName());

                liste.addElement(enumeration.nextElement());
            }
        }

        return fileexsists;
    }

    /**
     * Change le nom du inputFileZIP zip, avec un cheminputStreamcomplet
     */
    public void setNom(String nom) {
        inputFileZIP = nom;
    }

    /**
     * Donne un vecteur titre. Peut servir pour l'ent^te d'un JTable
     */
    public static Vector getTitre() {
        Vector titre = new Vector();
        titre.addElement("Nom");
        titre.addElement("Taille");
        titre.addElement("Compression");
        titre.addElement("Date");
        titre.addElement("Commentaire");
        return titre;
    }

    public Vector contenu() {
        int nb = liste.size();
        Vector donnees = new Vector();
        for (int i = 0; i < nb; i++) {
            Vector ligne = new Vector();
            ZipEntry entre = (ZipEntry) liste.elementAt(i);
            ligne.addElement(entre.getName());
            ligne.addElement("" + entre.getSize());
            long j = entre.getCompressedSize();
            if (j >= 0)
                ligne.addElement("" + j);
            else
                ligne.addElement("");
            Date date = new Date(entre.getTime());
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTime(date);
            ligne.addElement(calendrier.get(Calendar.HOUR_OF_DAY) + "h" + calendrier.get(Calendar.MINUTE) + "m"
                    + calendrier.get(Calendar.SECOND) + "s le " + calendrier.get(Calendar.DAY_OF_MONTH) + "/"
                    + calendrier.get(Calendar.MONTH) + "/" + calendrier.get(Calendar.YEAR));
            ligne.addElement(entre.getComment());
            donnees.addElement(ligne);
        }
        return donnees;
    }

    /**
     * Donne une chaine repr�sentant la caract�ristique du inputFileZIP <BR>
     * 
     * @param place
     *            Rang de la carat�ristique. <BR>
     * @param numero
     *            Num�ro du inputFileZIP contenu dans le zip <BR>
     *            Exemple : <BR>
     *            Pour avoir la taille de compression du inputFileZIP num�ro 5
     *            faire : <BR>
     *            String taille=zip.get(ZIP.placeCompression,5);
     */
    public String get(int place, int numero) {
        // R�cup�ration des caract�ristiques du inputFileZIP
        ZipEntry entre = (ZipEntry) liste.elementAt(numero);
        // Selon la caract�ristique demand�e, retourner la valeur corespondante
        switch (place) {
        // Le commentaire
        case placeCommentaire:
            return entre.getComment();
            // La taille de compression
        case placeCompression:
            long j = entre.getCompressedSize();
            if (j >= 0)
                return "" + j;
            return "";
            // La date du inputFileZIP
        case placeDate:
            // Transformation de la date exprim�e en milliseconde, en date
            // plus
            // courante
            Date date = new Date(entre.getTime());
            // Mise en place d'un calendrier � partir de la date
            Calendar calendrier = Calendar.getInstance();
            calendrier.setTime(date);
            // Renvoie des donn�es
            return calendrier.get(Calendar.HOUR_OF_DAY) + "h" + calendrier.get(Calendar.MINUTE) + "m"
                    + calendrier.get(Calendar.SECOND) + "s le " + calendrier.get(Calendar.DAY_OF_MONTH) + "/"
                    + calendrier.get(Calendar.MONTH) + "/" + calendrier.get(Calendar.YEAR);
            // Le nom du inputFileZIP
        case placeNom:
            return entre.getName();
            // La taille r�elle du inputFileZIP
        case placeTaille:
            return "" + entre.getSize();
        }
        // Renvoie la cha�ne vide si la caract�ristique n'existe pas
        return "";
    }

    /**
     * Ajoute un inputFileZIP au inputFileZIP zip
     */
    public void ajouteFichier(File fichier) {
        ajouteFichier(fichier, null);
    }

    /**
     * Ajoute un inputFileZIP au inputFileZIP zip avec un commentaire
     */
    public void ajouteFichier(File fichier, String commentaire) {
        // Transforme le inputFileZIP en entr�e du zip
        ZipEntry entre = new ZipEntry(fichier.getAbsolutePath().substring(3).replace(separ, '/'));
        entre.setSize(fichier.length());
        entre.setTime(fichier.lastModified());
        if (commentaire != null)
            entre.setComment(commentaire);
        // Ajoute l'�ntr�e
        liste.addElement(entre);
    }

    /**
     * Retire le inputFileZIP du zip
     */
    public void retire(File fichier) {
        // Cacul le nom de l'entr�e, en majuscule, corespondante au inputFileZIP
        String s = fichier.getAbsolutePath().substring(3).replace(separ, '/').toUpperCase();
        // Chercher le rang de l'�ntr�e corespondante
        int rang = -1;
        int nb = liste.size();
        for (int i = 0; (i < nb) && (rang < 0); i++) {
            ZipEntry entre = (ZipEntry) liste.elementAt(i);
            if (entre.getName().toUpperCase().equals(s))
                rang = i;
        }
        // Si l'�ntr�e existe, la retir�e
        if (rang >= 0)
            liste.removeElementAt(rang);
    }

    /**
     * @return Le nom du inputFileZIP
     */
    public int getNombreFichier() {
        return liste.size();
    }

    /**
     * Retire l'�l�lement num�ro numero
     */
    public void retire(int numero) {
        liste.removeElementAt(numero);
    }

    /**
     * Zip les fichiers dans le nom du zip correspondant. Met � jour les
     * changements.
     */
    public void ziper() throws Exception {
        // Si le inputFileZIP n'existe pas, le cr�er
        File f = new File(inputFileZIP);
        if (!f.exists())
            f.createNewFile();
        // Ouverture du inputFileZIP zip en �criture
        ZipOutputStream zos = new ZipOutputStream(new FileOutputStream(inputFileZIP));
        // Pour chaque entr�e du zip
        int nb = liste.size();
        for (int i = 0; i < nb; i++) {
            // R�cup�rer l'�ntr�e courante
            ZipEntry entre = (ZipEntry) liste.elementAt(i);
            // R�cup�rer le nom de l'entr�e
            String nom = entre.getName();
            // Ajouter l'entr�e au inputFileZIP physique zip
            zos.putNextEntry(entre);
            // Ouvrir l'entr�e en lecture
            FileInputStream fis = new FileInputStream("C:\\" + nom.replace('/', separ));
            // Ziper l'entr�e dans le inputFileZIP zip
            byte[] tab = new byte[4096];
            int lu = -1;
            do {
                lu = fis.read(tab);
                if (lu > 0)
                    zos.write(tab, 0, lu);
            }
            while (lu > 0);
            // Fermer l'entr�e
            fis.close();
        }
        // Force � finir le zipage, si jamais il reste des bits non trait�s
        zos.flush();
        // Ferme le inputFileZIP zip
        zos.close();
    }

    /**
     * D�zipe le inputFileZIP dans le dossier en param�tre. Respect du nom des
     * dossiers et sous-dossiers
     */
    public void deziper(File dossier) throws Exception {
        // Ouverture du inputFileZIP zip en lecture
        ZipInputStream zis = new ZipInputStream(new FileInputStream(inputFileZIP));
        // R�cup�re les entr�es �ffectivement zip�es dans le zip
        ZipEntry ze = zis.getNextEntry();
        // Tant qu'il y a des entr�es
        while (ze != null) {

            // Cr�e le dossier contenant le inputFileZIP une fois d�ziper, si il
            // n'existe pas

            File f = new File(dossier.getAbsolutePath() + separ + ze.toString().replace('/', separ));

            f.getParentFile().mkdirs();

            // Ouvre l'entr�e le inputFileZIP � d�ziper en �criture, le cr�e
            // s'il n'existe pas

            FileOutputStream fos = new FileOutputStream(f);
            // D�zipe le inputFileZIP

            int lu = -1;
            byte[] tampon = new byte[4096];
            do {
                lu = zis.read(tampon);
                if (lu > 0)
                    fos.write(tampon, 0, lu);
            }
            while (lu > 0);
            // Finir proprement
            fos.flush();
            // Fermer le inputFileZIP � d�ziper
            fos.close();
            // Passer au suivant
            ze = zis.getNextEntry();
        }
        // Fermer le inputFileZIP zip
        zis.close();
    }

    /**
     * Modifie le commentaire du inputFileZIP num�ro numero
     */
    public void setCommentaire(int numero, String commentaire) {
        ZipEntry entre = (ZipEntry) liste.elementAt(numero);
        entre.setComment(commentaire);
    }

}
