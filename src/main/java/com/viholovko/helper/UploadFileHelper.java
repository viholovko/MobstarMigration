package com.viholovko.helper;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class UploadFileHelper {

    public String uploadFile(String fileName){
        String fromFile = "https://s3-eu-west-1.amazonaws.com/mobstar-1/"+fileName;
        if (fileName.contains("/")){
            fileName = fileName.replaceAll("/", "_");
        }
        String toFile = "/home/viholovko/Pictures/MigrationData/"+fileName;

        try {

            URL website = new URL(fromFile);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(toFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();
            return fileName;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public void deleteAllFilesFromDirectory(){
        String path="/home/viholovko/Pictures/MigrationData";
        File file = new File(path);
        File[] files = file.listFiles();
        for (File f:files) {
            if (f.isFile() && f.exists()) {
                f.delete();
            }
        }
    }

    public static boolean checkIfURLExists(String targetUrl) {
        HttpURLConnection httpUrlConn;
        try {
            httpUrlConn = (HttpURLConnection) new URL("https://s3-eu-west-1.amazonaws.com/mobstar-1/"+targetUrl).openConnection();

            httpUrlConn.setRequestMethod("HEAD");

            httpUrlConn.setConnectTimeout(30000);
            httpUrlConn.setReadTimeout(30000);

//            System.out.println("Response Code: " + httpUrlConn.getResponseCode());
//            System.out.println("Response Message: " + httpUrlConn.getResponseMessage());

            return (httpUrlConn.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
            return false;
        }
    }
}
