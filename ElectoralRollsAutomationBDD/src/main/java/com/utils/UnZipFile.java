package com.utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;


public class UnZipFile {
	
	

	public void zip() throws Exception {
		// TODO Auto-generated method stub
		String srcFolderPath = "./target/cucumber-reports/advanced-reports/cucumber-html-reports/";
		Thread.sleep(9000);	
		File srcFileObj = new File(srcFolderPath);
		if(srcFileObj.exists()) {
		System.out.println("src dir length : " + srcFileObj.list().length);
		}
		FileOutputStream fos = new FileOutputStream("./target/Cucumber-Reports_QA.zip");
	    ZipOutputStream zos = new ZipOutputStream(fos);
	    addDirToZipArchive(zos, new File(srcFolderPath), null);
	    zos.flush();
	    fos.flush();
	    zos.close();
	    fos.close();

	}
	
	public static void addDirToZipArchive(ZipOutputStream zos, File fileToZip, String parrentDirectoryName) throws Exception {
	    if (fileToZip == null || !fileToZip.exists()) {
	        return;
	    }

	    String zipEntryName = fileToZip.getName();
	    if (parrentDirectoryName!=null && !parrentDirectoryName.isEmpty()) {
	        zipEntryName = parrentDirectoryName + "/" + fileToZip.getName();
	    }

	    if (fileToZip.isDirectory()) {
	        System.out.println("+" + zipEntryName);
	        for (File file : fileToZip.listFiles()) {
	            addDirToZipArchive(zos, file, zipEntryName);
	        }
	    } else {
	        System.out.println("   " + zipEntryName);
	        byte[] buffer = new byte[1024];
	        FileInputStream fis = new FileInputStream(fileToZip);
	        zos.putNextEntry(new ZipEntry(zipEntryName));
	        int length;
	        while ((length = fis.read(buffer)) > 0) {
	            zos.write(buffer, 0, length);
	        }
	        zos.closeEntry();
	        fis.close();
	    }
	}

}
