package com.books.noteh5servers.utils;

import com.books.notebasecore.util.UnZip;
import org.apache.commons.io.FilenameUtils;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;

public class FileUploadUtils {

    public static int uploadFile(MultipartFile file, String directoryName) throws IOException {
        try {
            if (file.isEmpty()) {
                return 0; // next pls
            }
            String ext1 = FilenameUtils.getExtension(file.getOriginalFilename());
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(directoryName + file.getOriginalFilename());
            Files.write(path, bytes);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    /**
     * File Management
     * 
     * @Description
     * @author chen
     * @param file
     * @param directoryName
     * @return
     * @throws IOException
     */
    public static int fileManagement(MultipartFile file, String directoryName) throws IOException {
        try {
            if (file.isEmpty()) {
                return 0; // next pls
            }
            File directory = new File(directoryName);
            if (!directory.exists()) {
                directory.mkdirs();
            }
            byte[] bytes = file.getBytes();
            Path path = Paths.get(directoryName + file.getOriginalFilename());
            Files.write(path, bytes);
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int uploadPromotionFile(List<MultipartFile> files, String directoryName, String promotionPath,
            List<String> fNameList) throws IOException {
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    return 0; // next pls
                }
                File directory = new File(directoryName + promotionPath);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                byte[] bytes = file.getBytes();
                Path path = Paths.get(directoryName + promotionPath + file.getOriginalFilename());
                Files.write(path, bytes);
                fNameList.add(promotionPath + file.getOriginalFilename());
//                AmazonS3Util.uploadImage(directoryName, promotionPath + file.getOriginalFilename());
            }
            return 1;
        } catch (Exception e) {
            return 0;
        }
    }

    public static int uploadProductImagePackage(List<MultipartFile> files, String directoryName, String folderName,
            List<String> fNameList) throws IOException {
        try {
            for (MultipartFile file : files) {
                if (file.isEmpty()) {
                    continue;
                }
                String fileName = file.getOriginalFilename();

                String ext1 = FilenameUtils.getExtension(fileName);

                File directory = new File(directoryName + folderName);
                if (!directory.exists()) {
                    directory.mkdirs();
                }
                byte[] bytes = file.getBytes();
                if (ext1.equalsIgnoreCase("zip")) {
                    // image zip upload
                    Path path = Paths.get(directoryName + folderName + fileName);
                    Files.write(path, bytes);
                    UnZip unZip = new UnZip();
                    unZip.unZipIt(directoryName + folderName + fileName, directoryName + folderName, fNameList);
                    Files.delete(path);
                    if (fNameList != null && fNameList.size() > 0) {
                        for (int i = 0; i < fNameList.size(); i++) {
//                            AmazonS3Util.uploadImage(directoryName, folderName + fNameList.get(i));
                        }
                    }
                } else {
                    // single image upload
                    String spu = fileName.substring(0, 9);
                    File folderFile = new File(directoryName + folderName + spu);
                    if (!folderFile.exists()) {
                        folderFile.mkdirs();
                    }
                    Path path = Paths.get(directoryName + folderName + spu + "/" + fileName);
                    Files.write(path, bytes);
                    fNameList.add(spu + "/" + fileName);
//                    AmazonS3Util.uploadImage(directoryName, folderName + spu + "/" + fileName);
                }
            }
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            return 0;
        }

    }

    /**
     * List all the files and folders from a directory
     * 
     * @param directoryName
     *            to be listed
     */
    public static JSONArray listFilesAndFolders(String directoryName, String baseDir) {
        JSONArray jarr = new JSONArray();

        File directory = new File(directoryName);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        String tmpPath = "";
        if (!directoryName.equalsIgnoreCase(baseDir))
            tmpPath = directoryName.substring(baseDir.length(), directoryName.length());
        if (fList != null) {
            for (File file : fList) {
                if (!file.isHidden()) {
                    JSONObject jobj = new JSONObject();
                    try {
                        jobj.put("fileName", file.getName());
                        jobj.put("fileType", "D");
                        jobj.put("fURL", "");
                        jobj.put("fThumbNailURL", "");
                        jobj.put("fDIR", directoryName);
                        // System.out.println(file.getName());
                        if (file.isFile()) {
                            jobj.put("fURL", "/mmimage/" + tmpPath + file.getName());
                            jobj.put("fThumbNailURL", "");
                            jobj.put("fileType", "F");
                        }
                        jarr.put(jobj);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
            }
        }

        return jarr;
    }

    public static String createFolder(String dirName, String folderName) {
        File directory = new File(dirName + folderName);
        if (!directory.exists()) {
            directory.mkdir();
            // If you require it to make the entire directory path including
            // parents,
            // use directory.mkdirs(); here instead.
        }
        return dirName + folderName;
    }

    public static String deleteFile(String dirName) {
        File directory = new File(dirName);
        if (directory.exists()) {
            if (directory.isFile())
                directory.delete();
            else {
                String[] entries = directory.list();
                for (String s : entries) {
                    deleteFile(directory.getPath() + "/" + s);
                    directory.delete();
                }
            }

        }
        return dirName;
    }

    /**
     * List all the files under a directory
     * 
     * @param directoryName
     *            to be listed
     */
    public void listFiles(String directoryName) {
        File directory = new File(directoryName);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * List all the folder under a directory
     * 
     * @param directoryName
     *            to be listed
     */
    public void listFolders(String directoryName) {
        File directory = new File(directoryName);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isDirectory()) {
                System.out.println(file.getName());
            }
        }
    }

    /**
     * List all files from a directory and its subdirectories
     * 
     * @param directoryName
     *            to be listed
     */
    public void listFilesAndFilesSubDirectories(String directoryName) {
        File directory = new File(directoryName);
        // get all the files from a directory
        File[] fList = directory.listFiles();
        for (File file : fList) {
            if (file.isFile()) {
                System.out.println(file.getAbsolutePath());
            } else if (file.isDirectory()) {
                listFilesAndFilesSubDirectories(file.getAbsolutePath());
            }
        }
    }
    /*
     * public static void main (String[] args){ ListFilesUtil listFilesUtil =
     * new ListFilesUtil(); final String directoryLinuxMac
     * ="/Users/loiane/test"; //Windows directory example final String
     * directoryWindows ="C://test"; listFilesUtil.listFiles(directoryLinuxMac);
     * }
     */

    public static File[] readFilesFromNewUpload(String directoryName, String baseDir) {
        JSONArray jarr = new JSONArray();

        File directory = new File(directoryName);
        // get all the files from a directory
        File[] fList = directory.listFiles();

        return fList;
    }

    public static int moveFiles(String frmPath, String toPath) throws IOException {
        File srcFile = new File(frmPath);
        File destDir = new File(toPath);
        if (!destDir.exists())
            destDir.mkdirs();
        Path src = srcFile.toPath();
        Path dest = new File(destDir, srcFile.getName()).toPath();

        Path tmp = Files.move(src, dest, StandardCopyOption.REPLACE_EXISTING);
        if (tmp != null)
            return 1;
        return 0;
    }
}
