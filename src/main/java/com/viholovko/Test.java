package com.viholovko;

import java.io.File;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class Test {
    /** * @param args
     * @throws IOException
     * @throws ClientProtocolException
     */
    public static void main(String[] args) throws ClientProtocolException, IOException {

        HttpClient httpclient = new DefaultHttpClient();
        HttpPost httppost = new HttpPost("http://localhost:3000/api/v1/categories");

        httppost.addHeader("Session-Token", "db32280fb1146d2c299b0e964cd114bc8153387134416506de53316bf0d6d6de");

        File fileToUse = new File("https://s3-eu-west-1.amazonaws.com/mobstar-1/33ffd31e4b062ba562260d07768f2814.png");

        MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
        reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
        reqEntity.addTextBody("title", "TEST CATEGORY AWS", ContentType.APPLICATION_JSON);
        reqEntity.addTextBody("gradient_start", "#07faf0" , ContentType.APPLICATION_JSON);
        reqEntity.addTextBody("gradient_end", "#03a9f4", ContentType.APPLICATION_JSON);
        reqEntity.addBinaryBody("image", fileToUse, ContentType.IMAGE_PNG, "33ffd31e4b062ba562260d07768f2814.png");

        HttpEntity entity = reqEntity.build();
        httppost.setEntity(entity);

        HttpResponse response = httpclient.execute(httppost);
        System.out.println( response ) ;

        HttpEntity resEntity = response.getEntity();
        System.out.println( resEntity ) ;
        System.out.println( EntityUtils.toString(resEntity) );

        EntityUtils.consume(resEntity);
        httpclient.getConnectionManager().shutdown();
    }
}