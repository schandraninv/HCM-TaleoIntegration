package com.invensense.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;

public class ZipUsingJavaUtil {
	 @SuppressWarnings("finally")
	    public boolean zipFiles(String srcFolder, String destZipFile) {
	        boolean result = false;
	        try {
	            System.out.println("Program Start zipping the given files");
	            /*
	             * send to the zip procedure
	             */
	            zipFolder(srcFolder, destZipFile);
	            result = true;
	            System.out.println("Given files are successfully zipped");
	        } catch (Exception e) {
	            System.out.println("Some Errors happned during the zip process");
	        } finally {
	            return result;
	        }
	    }

	    /*
	     * zip the folders
	     */
	    private void zipFolder(String srcFolder, String destZipFile) throws Exception {
	        ZipOutputStream zip = null;
	        FileOutputStream fileWriter = null;
	        /*
	         * create the output stream to zip file result
	         */
	        fileWriter = new FileOutputStream(destZipFile);
	        zip = new ZipOutputStream(fileWriter);
	        /*
	         * add the folder to the zip
	         */
	        addFolderToZip("", srcFolder, zip);
	        /*
	         * close the zip objects
	         */
	        zip.flush();
	        zip.close();
	    }

	    /*
	     * recursively add files to the zip files
	     */
	    private void addFileToZip(String path, String srcFile, ZipOutputStream zip, boolean flag) throws Exception {
	        /*
	         * create the file object for inputs
	         */
	        File folder = new File(srcFile);

	        /*
	         * if the folder is empty add empty folder to the Zip file
	         */
	        if (flag == true) {
	            zip.putNextEntry(new ZipEntry(path + "/" + folder.getName() + "/"));
	        } else { /*
	                 * if the current name is directory, recursively traverse it
	                 * to get the files
	                 */
	            if (folder.isDirectory()) {
	                /*
	                 * if folder is not empty
	                 */
	                addFolderToZip(path, srcFile, zip);
	            } else {
	                /*
	                 * write the file to the output
	                 */
	                byte[] buf = new byte[1024];
	                int len;
	                FileInputStream in = new FileInputStream(srcFile);
	                zip.putNextEntry(new ZipEntry(path + "/" + folder.getName()));
	                while ((len = in.read(buf)) > 0) {
	                    /*
	                     * Write the Result
	                     */
	                    zip.write(buf, 0, len);
	                }
	            }
	        }
	    }

	    /*
	     * add folder to the zip file
	     */
	    private void addFolderToZip(String path, String srcFolder, ZipOutputStream zip) throws Exception {
	        File folder = new File(srcFolder);

	        /*
	         * check the empty folder
	         */
	        if (folder.list().length == 0) {
	            System.out.println(folder.getName());
	            addFileToZip(path, srcFolder, zip, true);
	        } else {
	            /*
	             * list the files in the folder
	             */
	            for (String fileName : folder.list()) {
	                if (path.equals("")) {
	                    addFileToZip(folder.getName(), srcFolder + "/" + fileName, zip, false);
	                } else {
	                    addFileToZip(path + "/" + folder.getName(), srcFolder + "/" + fileName, zip, false);
	                }
	            }
	        }
	    }
	public static void main(String[] args) {
		String zipFile = "D://opt//FBL_20170102150054684/FBL_20170102150054684.zip";

		String srcDir = "D://opt//tomcat//logs//Files//FBL_20170102150054684//Person";
		ZipUsingJavaUtil zipUsingJavaUtil = new ZipUsingJavaUtil();
		zipUsingJavaUtil.zipFiles(srcDir,zipFile);

	}

}
