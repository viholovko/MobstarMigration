package com.viholovko;

import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.nio.channels.Channels;
import java.nio.channels.ReadableByteChannel;

public class UploadFileFromURL {

    public static void main(String[] args) {
        String fromFile = "https://s3-eu-west-1.amazonaws.com/mobstar-1/d993634ec524f24b8654fabdc38ccf95.png";
        String toFile = "/home/viholovko/Pictures/d993634ec524f24b8654fabdc38ccf95.png";

        try {

            URL website = new URL(fromFile);
            ReadableByteChannel rbc = Channels.newChannel(website.openStream());
            FileOutputStream fos = new FileOutputStream(toFile);
            fos.getChannel().transferFrom(rbc, 0, Long.MAX_VALUE);
            fos.close();
            rbc.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
