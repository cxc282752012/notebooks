package com.books.notebasecore.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.List;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.apache.commons.io.FilenameUtils;

public class UnZip {

    /**
     * Unzip it
     * 
     * @param zipFile
     *            input zip file
     * @param output
     *            zip file output folder
     */
    public void unZipIt(String zipFile, String outputFolder, List<String> fNameList) {
        byte[] buffer = new byte[1024];
        try {
            // create output directory is not exists
            // get the zip file content
            ZipInputStream zis = new ZipInputStream(new FileInputStream(zipFile));
            // get the zipped file list entry
            ZipEntry ze = zis.getNextEntry();
            while (ze != null) {
                String fileName = ze.getName();
                File newFile = new File(outputFolder + fileName);
                System.out.println("file unzip : " + newFile.getAbsoluteFile());
                // create all non exists folders
                // else you will hit FileNotFoundException for compressed folder
                // new File(newFile.getParent()).mkdirs();

                if (!ze.isDirectory()) {
                    String ext1 = FilenameUtils.getExtension(newFile.getName());
                    ext1 = ext1.toLowerCase();
                    if (ext1.contains("jpg") || ext1.contains("png") || ext1.contains("jpeg")) {

                        String nAbPath = newFile.getAbsoluteFile().getAbsolutePath();
                        String[] pathArr = null;
                        if (nAbPath.contains("/")) {
                            pathArr = nAbPath.split("/");
                        } else {
                            pathArr = nAbPath.split("\\\\");
                        }

                        nAbPath = pathArr[pathArr.length - 2] + "/" + pathArr[pathArr.length - 1];

                        File nFile = new File(outputFolder + nAbPath);
                        new File(nFile.getParent()).mkdirs();

                        FileOutputStream fos = new FileOutputStream(nFile);

                        int len;
                        while ((len = zis.read(buffer)) > 0) {
                            fos.write(buffer, 0, len);
                        }

                        fos.close();
                        fNameList.add(nAbPath);
                    }
                }
                zis.closeEntry();
                ze = zis.getNextEntry();
            }
            zis.closeEntry();
            zis.close();

            System.out.println("Done");

        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}