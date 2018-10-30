package com.viholovko.helper;

import com.viholovko.model.migration.UserMigration;
import com.viholovko.model.old.Category;
import com.viholovko.model.old.Entrie;
import com.viholovko.model.old.UserOld;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import javax.activation.MimetypesFileTypeMap;
import java.io.File;
import java.io.IOException;
import java.util.List;

public class SendCategoryToServerHelper {

    public boolean sendCategoryToServer(Category category, String fileName, String token) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPost httppost = new HttpPost("http://localhost:3000/api/v1/categories");

            httppost.addHeader("Session-Token", token);

            File fileToUse = new File("/home/viholovko/Pictures/MigrationData/" + fileName);

            MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
            reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addTextBody("title", category.getName(), ContentType.APPLICATION_JSON);
            reqEntity.addTextBody("gradient_start", "#07faf0", ContentType.APPLICATION_JSON);
            reqEntity.addTextBody("gradient_end", "#03a9f4", ContentType.APPLICATION_JSON);
            reqEntity.addTextBody("old_id", String.valueOf(category.getId()), ContentType.APPLICATION_JSON);
            reqEntity.addBinaryBody("image", fileToUse, ContentType.IMAGE_PNG, fileName);

            HttpEntity entity = reqEntity.build();
            httppost.setEntity(entity);

            HttpResponse response = null;

            response = httpclient.execute(httppost);

            HttpEntity resEntity = response.getEntity();

            EntityUtils.consume(resEntity);

            httpclient.getConnectionManager().shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    public boolean sendAvatarToUser(UserOld user, String fileName, String token) {
        try {
            HttpClient httpclient = new DefaultHttpClient();
            HttpPut httppost = new HttpPut("http://localhost:3000/api/v1/users/update_profile");

            httppost.addHeader("Session-Token", token);

            File fileToUse = new File("/home/viholovko/Pictures/MigrationData/" + fileName);

            MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
            reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
            reqEntity.addBinaryBody("avatar", fileToUse, checkContentType(fileName), fileName);

            HttpEntity entity = reqEntity.build();
            httppost.setEntity(entity);

            HttpResponse response = null;

            response = httpclient.execute(httppost);

            HttpEntity resEntity = response.getEntity();

            EntityUtils.consume(resEntity);

            httpclient.getConnectionManager().shutdown();
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }

    private ContentType checkContentType(String fileName) {
        if (fileName.toLowerCase().contains(".jpg")){
            return ContentType.IMAGE_JPEG;
        } else if (fileName.toLowerCase().contains(".png")){
            return ContentType.IMAGE_PNG;
        }

        return null;
    }

    public void createPostWithAttachments(Entrie entrie, UserMigration user) {
        try {
                String fileName ="";
                String toFile ="";

                if (!entrie.getType().equals("youtube")){
                    if (entrie.isUseThumb()){
                        fileName = entrie.getThumbnail();
                    } else {
                        fileName = entrie.getSource();
                    }

                    if (fileName!=null && fileName.contains("/")){
                        fileName = fileName.replaceAll("/", "_");
                    }
                } else {
                    fileName = getYouTubeId(entrie.getThumbnail());
                }

                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://localhost:3000/api/v1/posts");

                httppost.addHeader("Session-Token", "user_" + user.getOld_id());

                File fileToUse = new File("/home/viholovko/Pictures/MigrationData/" + fileName);

                MultipartEntityBuilder reqEntity = MultipartEntityBuilder.create();
                reqEntity.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);
                reqEntity.addTextBody("title", entrie.getName(), ContentType.APPLICATION_JSON);
                reqEntity.addTextBody("category_id", String.valueOf(entrie.getCategory_id()), ContentType.APPLICATION_JSON);
                if (entrie.getType().equals("image")) {
                    reqEntity.addBinaryBody("image_attachments_attributes[][file]", fileToUse, ContentType.parse(new MimetypesFileTypeMap().getContentType(fileToUse.getPath())), fileName);
                } else if (entrie.getType().equals("video")) {
                    reqEntity.addBinaryBody("video_attachments_attributes[][file]", fileToUse, ContentType.parse(new MimetypesFileTypeMap().getContentType(fileToUse.getPath())), fileName);
                } else if (entrie.getType().equals("audio")) {
                    reqEntity.addBinaryBody("audio_attachments_attributes[][file]", fileToUse, ContentType.parse(new MimetypesFileTypeMap().getContentType(fileToUse.getPath())), fileName);
                } else {
                    reqEntity.addTextBody("youtube_id", fileName, ContentType.APPLICATION_JSON);
                }

                HttpEntity entity = reqEntity.build();
                httppost.setEntity(entity);

                HttpResponse response = null;

                response = httpclient.execute(httppost);

                HttpEntity resEntity = response.getEntity();

                EntityUtils.consume(resEntity);

                httpclient.getConnectionManager().shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private String getYouTubeId(String thumbnail) {
        thumbnail = thumbnail.replaceAll("https://i.ytimg.com/vi/", "");

        int position = thumbnail.indexOf("/");

        return thumbnail.substring(0, position);
    }
}
