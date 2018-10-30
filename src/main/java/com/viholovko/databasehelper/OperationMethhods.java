package com.viholovko.databasehelper;

import com.viholovko.helper.SendCategoryToServerHelper;
import com.viholovko.helper.UploadFileHelper;
import com.viholovko.model.migration.*;
import com.viholovko.model.old.*;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;


public class OperationMethhods {

    public static void addUsersSettings(List<UserMigration> users) throws SQLException {
        System.out.println("Add Settings for Users");

        int count = users.size();

        NewDatabaseHelper.trancateTable("settings");

        int current = 0;


        List<UserSetting> userSettings = new ArrayList<>();
            for (UserMigration user : users) {
                if (user.getId() > 1) {
                    UserSetting setting = new UserSetting(user.getId(), user.getCreated_at(), user.getUpdated_at());

                    userSettings.add(setting);
                    current += 1;
                }
            printProgress(count, current);
        }

        NewDatabaseHelper.insertUserSettingsToDatabase(userSettings);

        System.out.println();
        System.out.println("Settings for Users successfully added");
    }

    public static void checkMemberCategory(List<CategoryMigration> categories, List<UserMigration> users) throws SQLException {
        System.out.println("Start to add Category to User");


        List<UserMigration> userMigrations = new ArrayList<>();

        String strSQL = "SELECT category_id AS id, user_id AS user_id FROM mentor_info";
        ResultSet rs = OldDatabaseHelper.getResultByRequest(strSQL);

        int count = 0;
        int current = 0;

        while (rs.next()) {
            int categoryId = getCategoryId(rs.getInt("id"), categories);
            int userId = getUserId(rs.getInt("user_id"), users);

            if (userId > 0) {
                UserMigration user = new UserMigration();
                user.setId(userId);
                user.setCategory_id(categoryId);
                user.setRole_id(1);
                userMigrations.add(user);
                current += 1;
                count += 1;
            }
        }
        printProgress(count, current);

        NewDatabaseHelper.updateUserCategoryInDatabase(userMigrations);

        System.out.println();
        System.out.println("Category to User added successfully");
    }

    private static int getUserId(Integer user_id, List<UserMigration> users) {
        for (UserMigration user : users){
            if (user.getOld_id() == user_id){
                return user.getId();
            }
        }
        return 0;
    }

    public static void migrateFAQ() throws SQLException {
        System.out.println("Start to migrate FAQ");

        List<FAQ> faqList = new ArrayList<>();

        int count = 0;
        int current = 0;

        String strSQL = "SELECT * FROM faq";
        ResultSet rs = OldDatabaseHelper.getResultByRequest(strSQL);

        while (rs.next()) {
            String data = rs.getString("created_at");
            faqList.add(new FAQ(rs.getInt("id"), rs.getString("question"), rs.getString("answer"), rs.getDate("created_at"), rs.getDate("updated_at")));
            count += 1;
        }

        NewDatabaseHelper.trancateTable("frequently_asked_questions");

        List<FAQMigration> faqMigrationList = new ArrayList<>();
        for (FAQ faq : faqList) {
            faqMigrationList.add(new FAQMigration(faq.getId(), faq.getQuestion(), faq.getAnswer(), faq.getAnswer(), 1, faq.getCreated_at(), faq.getUpdated_at()));
            current += 1;
            printProgress(count, current);
        }

        NewDatabaseHelper.insertFAQToDatabase(faqMigrationList);

        System.out.println();
        System.out.println("FAQ successfully migrated");
    }

    public static List<UserMigration>  migrateUser() throws SQLException {
        System.out.println("Start to migrate User");

        int count = OldDatabaseHelper.getCountInTable("users");

        int page = 0;
        int per_page = 250;

//        NewDatabaseHelper.trancateTable("users");

        int current = 0;

        while ((page - 1) * per_page < count) {
            List<UserOld> userOlds = new ArrayList<>();

            String strSQL = "SELECT * FROM  users LEFT JOIN accounts ON users.id = accounts.user_id ORDER BY users.id ASC LIMIT " + per_page + " OFFSET " + (page * per_page) + ";";
            ResultSet rs = OldDatabaseHelper.getResultByRequest(strSQL);

            while (rs.next()) {
                UserAccount userAccount = new UserAccount(rs.getString("type"), rs.getString("social_id"),
                        rs.getString("display_name"), rs.getString("full_name"), rs.getBoolean("active"));
                UserOld userOld = new UserOld();
                userOld.setId(rs.getInt("id"));
                userOld.setDisplay_name(rs.getString("display_name"));
                userOld.setFull_name(rs.getString("full_name"));
                userOld.setEmail(rs.getString("email"));
                userOld.setPassword(rs.getString("password"));
                userOld.setBio(rs.getString("bio"));
                userOld.setDob(rs.getDate("dob"));
                userOld.setCreated_at(rs.getDate("created_at"));
                userOld.setUpdated_at(rs.getDate("updated_at"));
                userOld.setUserAccount(userAccount);

                userOlds.add(userOld);
            }

            List<UserMigration> userMigrations = new ArrayList<>();
            for (UserOld userOld : userOlds) {
                UserMigration userM = new UserMigration();
                userM.setFull_name(userOld.getFull_name());
                userM.setDisplay_name(userOld.getDisplay_name());
                userM.setBio(userOld.getBio());
                userM.setBirthdate(userOld.getDob());
                userM.setEmail(userOld.getEmail());
                userM.setEncrypted_password(userOld.getPassword());
                userM.setSocial_id(userOld.getUserAccount().getSocial_id());
                userM.setSocial_type(userOld.getUserAccount().getType());
                userM.setRole_id(2);
                userM.setCreated_at(userOld.getCreated_at());
                userM.setUpdated_at(userOld.getUpdated_at());
                userM.setConfirmed(true);
                userM.setOld_id(userOld.getId());

                userMigrations.add(userM);
                current += 1;
            }

            NewDatabaseHelper.insertUserToDatabase(userMigrations);

            printProgress(count, current);

            page += 1;
        }
        System.out.println();

        System.out.println("Users successfully migrated");

        return getAllUsers();
    }

    public static void migrateUserAvatar() throws SQLException {
        System.out.println("Start to migrate User's Avatar");

        int count = OldDatabaseHelper.getCountInTable("users WHERE LENGTH(profile_image)>0;");

        int page = 0;
        int per_page = 250;
        int current = 0;

        while ((page - 1) * per_page < count) {
            List<UserOld> userOlds = new ArrayList<>();

            String strSQL = "SELECT id, profile_image, created_at, updated_at FROM users WHERE LENGTH(profile_image)>0 ORDER BY users.id ASC LIMIT " + per_page + " OFFSET " + (page * per_page) + ";";
            ResultSet rs = OldDatabaseHelper.getResultByRequest(strSQL);

            while (rs.next()) {
                UserOld userOld = new UserOld();
                userOld.setId(rs.getInt("id"));
                userOld.setProfileImage(rs.getString("profile_image"));
                userOld.setCreated_at(rs.getDate("created_at"));
                userOld.setUpdated_at(rs.getDate("updated_at"));

                userOlds.add(userOld);
            }

            for (UserOld user : userOlds) {
                new UploadFileHelper().uploadFile(user.getProfileImage());
            }

            for (UserOld user : userOlds) {
                String fileName = user.getProfileImage().replaceAll("/", "_");
                current += 1;

                new SendCategoryToServerHelper().sendAvatarToUser(user, fileName, "user_" + user.getId());
            }

            printProgress(count, current);

            page += 1;

            new UploadFileHelper().deleteAllFilesFromDirectory();
        }

        System.out.println();
        System.out.println("Users successfully migrated");
    }

    public static List<CategoryMigration> migrateCategory() throws SQLException {
        System.out.println("Start to migrate Category");

        List<Category> categoryList = new ArrayList<>();

        int count = 0;
        int current = 1;

        String strSQL = "SELECT * FROM categories";
        ResultSet rs = OldDatabaseHelper.getResultByRequest(strSQL);

        while (rs.next()) {
            categoryList.add(new Category(rs.getInt("id"), rs.getBoolean("active"), rs.getString("name"),
                    rs.getString("description"), rs.getBoolean("coming_soon"), rs.getString("image"), rs.getDate("created_at"),
                    rs.getDate("updated_at")));
            count += 1;
        }

        NewDatabaseHelper.deleteByRequest("DELETE FROM categories WHERE old_id NOTNULL");

        printProgress(count, current);

        for (Category cat : categoryList) {
            new UploadFileHelper().uploadFile(cat.getImage());
        }
        current = 0;
        for (Category cat : categoryList) {
            String fileName = cat.getImage().replaceAll("/", "_");
            current += 1;
            printProgress(count, current);

            System.out.println(new SendCategoryToServerHelper().sendCategoryToServer(cat, fileName, "admin"));
        }

        NewDatabaseHelper.updateCategoryInDatabase(categoryList);

        System.out.println();

        new UploadFileHelper().deleteAllFilesFromDirectory();

        System.out.println("Category successfully migrated");

        return getAllCategories();
    }

    private static void printProgress(long total, long current) {
        StringBuilder string = new StringBuilder(140);
        int percent = (int) (current * 100 / total);
        string
                .append('\r')
                .append(String.join("", Collections.nCopies(percent == 0 ? 2 : 2 - (int) (Math.log10(percent)), " ")))
                .append(String.format(" %d%% [", percent))
                .append(String.join("", Collections.nCopies(percent, "=")))
                .append('>')
                .append(String.join("", Collections.nCopies(100 - percent, " ")))
                .append(']')
                .append(String.join("", Collections.nCopies((int) (Math.log10(total)) - (int) (Math.log10(current)), " ")))
                .append(String.format(" %d/%d", current, total));

        System.out.print(string);
    }

    public static void migrateUsersFollowers() throws SQLException {
        System.out.println("Start to migrate User's Followers");

        int count = OldDatabaseHelper.getCountInTable("followers");

        int page = 0;
        int per_page = 125;
        int bad = 0;

        NewDatabaseHelper.trancateTable("relationships");

        int current = 0;

        while ((page - 1) * per_page < count) {
            List<Follower> followers = new ArrayList<>();

            String strSQL = "SELECT * FROM followers ORDER BY followers.id ASC LIMIT " + per_page + " OFFSET " + (page * per_page) + ";";
            ResultSet rs = OldDatabaseHelper.getResultByRequest(strSQL);

            while (rs.next()) {
                followers.add(new Follower(rs.getInt("follower"), rs.getInt("star"), rs.getDate("created_at"), rs.getDate("updated_at")));
            }

            List<Relation> relations = new ArrayList<>();
            for (Follower follower : followers) {
                int follower_id = 0;
                int followed_id = 0;

                String foolowerSQL = "SELECT * FROM  users WHERE old_id=" + follower.getFollowerId() + ";";
                String foolowedSQL = "SELECT * FROM  users WHERE old_id=" + follower.getStar() + ";";
                ResultSet followerRS = NewDatabaseHelper.getResultByRequest(foolowerSQL);

                while (followerRS.next()) {
                    follower_id = followerRS.getInt("id");
                }

                ResultSet followedRS = NewDatabaseHelper.getResultByRequest(foolowedSQL);
                while (followedRS.next()) {
                    followed_id = followedRS.getInt("id");
                }

                if (follower_id > 0 && followed_id > 0 && (followed_id != follower_id)) {
                    relations.add(new Relation(follower_id, followed_id, true, follower.getCreated_at(), follower.getUpdated_at()));
                } else {
                    bad += 1;
                }

                current += 1;
            }

            printProgress(count, current);

            NewDatabaseHelper.insertRelationToDatabase(relations);

            page += 1;
        }
        System.out.println();
        System.out.println(bad);

        System.out.println("User's Followers successfully migrated");
    }

    public static List<Post> migratePosts(List<UserMigration> userMigrations1, List<CategoryMigration> categories1) throws SQLException {
        System.out.println("Start to migrate Posts");

        int count = OldDatabaseHelper.getCountInTable("entries");
        int current = 0;

        NewDatabaseHelper.trancateTable("posts");

        List<UserMigration> userMigrations = getAllUsers();
        List<Entrie> entries = getAllEntries();
        List<CategoryMigration> categories = getAllCategories();
        List<Entrie> entriesCopy = new ArrayList<>(entries);

        new UploadFileHelper().deleteAllFilesFromDirectory();

        for (UserMigration userMigration : userMigrations) {
            List<Entrie> userEntries = allUsersEntreis(entries, userMigration.getOld_id());

            if (userEntries.size()>0) {
                for (Entrie entrie : userEntries) {
                    entrie.setCategory_id(getCategoryId(entrie.getCategory_id(), categories));
                    new SendCategoryToServerHelper().createPostWithAttachments(entrie, userMigration);
                }
                new UploadFileHelper().deleteAllFilesFromDirectory();
                current+= userEntries.size();
                printProgress(count, current);
            }
        }

        System.out.println();

        current = 0;
        List<Entrie> update = new ArrayList<>();
        for (Entrie entrie: entriesCopy){

            update.add(entrie);
            if (current % 10000 == 0) {
                System.out.print(".");

                NewDatabaseHelper.updatePostInDatabase(update);

                update.clear();
            }
        }

        NewDatabaseHelper.updatePostInDatabase(update);

        System.out.println("Posts successfully migrated");


        return getAllPosts();
    }

    private static List<Post> getAllPosts() throws SQLException {
        List<Post> posts = new ArrayList<>();

        System.out.print("Prepare Posts ");

        String strSQL = "SELECT * FROM posts ORDER BY posts.id ASC;";
        ResultSet rs = NewDatabaseHelper.getResultByRequest(strSQL);

        while (rs.next()) {
            posts.add(new Post(rs.getInt("id"), rs.getInt("user_id"), rs.getInt("category_id"), rs.getInt("old_id")));
            System.out.print(".");
        }

        System.out.println();

        return posts;
    }

    private static int getCategoryId(int category_id, List<CategoryMigration> categories) {
        for (CategoryMigration category : categories){
            if (category.getOld_id()== category_id){
                return category.getId();
            }
        }
        return 0;
    }

    private static List<Entrie> allUsersEntreis(List<Entrie> entries, int old_id) {
        List<Entrie> userEntries = new ArrayList<>();

        Iterator<Entrie> it = entries.iterator();
        while (it.hasNext()) {
            Entrie entrie = it.next();
            if (entrie.getUser_id() == old_id) {



                if (!entrie.getType().contains("youtube") && entrie.getSource()!=null) {
                    if (new UploadFileHelper().checkIfURLExists(entrie.getSource())) {
                        new UploadFileHelper().uploadFile(entrie.getSource());
                    } else if (new UploadFileHelper().checkIfURLExists(entrie.getThumbnail())){
                        entrie.setType("image");
                        entrie.setUseThumb(true);
                        new UploadFileHelper().uploadFile(entrie.getThumbnail());
                        entrie.setStatus(true);
                    }
                }

                userEntries.add(entrie);
                it.remove();
            }
        }

        return userEntries;
    }

   public static List<CategoryMigration> getAllCategories() throws SQLException {
        List<CategoryMigration> categories = new ArrayList<>();

        System.out.print("Prepare Categories ");

        String strSQL = "SELECT * FROM  categories ORDER BY categories.id ASC;";
        ResultSet rs = NewDatabaseHelper.getResultByRequest(strSQL);

        while (rs.next()) {
            categories.add(new CategoryMigration(rs.getInt("id"), rs.getInt("old_id")));
            System.out.print(".");
        }

        System.out.println();

        return categories;
    }

    private static List<Entrie> getAllEntries() throws SQLException {
        List<Entrie> entries = new ArrayList<>();

        int count = OldDatabaseHelper.getCountInTable("entries");
        int page = 0;
        int per_page = 250;

        System.out.print("Prepare Old Posts  ");

        while ((page - 1) * per_page < count) {
            String strSQL = "SELECT entries.id AS id, entries.user_id, entries.category_id, description, thumbnail,  entries.name, entries.type, entries.status, language, is_public, on_youtube, files.name AS file_name, entries.created_at, entries.updated_at FROM entries LEFT JOIN files ON entries.id = files.entry_id ORDER BY entries.id ASC LIMIT " + per_page + " OFFSET " + (page * per_page) + ";";
            ResultSet rs = OldDatabaseHelper.getResultByRequest(strSQL);

            while (rs.next()) {
                Entrie entrie = new Entrie(rs.getInt("id"), rs.getInt("user_id"),
                        rs.getInt("category_id"), rs.getString("name"), rs.getString("type"),
                        rs.getString("description"), rs.getBoolean("status"), rs.getBoolean("is_public"), rs.getBoolean("on_youtube"),
                        rs.getString("thumbnail"),rs.getString("language"), rs.getString("file_name"), rs.getDate("created_at"), rs.getDate("updated_at"));

                entries.add(entrie);
            }
            if (page % 25 == 0) {
                System.out.print(".");
            }
            page += 1;
        }

        System.out.println();

        return entries;
    }

    public static List<UserMigration> getAllUsers() throws SQLException {
        List<UserMigration> userMigrations = new ArrayList<>();

        int count = NewDatabaseHelper.getCountInTable("users");

        int page = 0;
        int per_page = 1000;

        System.out.print("Prepare Users      ");

        while ((page - 1) * per_page < count) {
            String strSQL = "SELECT * FROM  users ORDER BY users.id ASC LIMIT " + per_page + " OFFSET " + (page * per_page) + ";";
            ResultSet rs = NewDatabaseHelper.getResultByRequest(strSQL);

            while (rs.next()) {
                UserMigration user = new UserMigration();
                user.setId(rs.getInt("id"));
                user.setOld_id(rs.getInt("old_id"));
                user.setCreated_at(rs.getDate("created_at"));
                user.setUpdated_at(rs.getDate("updated_at"));

                userMigrations.add(user);
            }
            if (page % 25 == 0) {
                System.out.print(".");
            }
            page += 1;
        }

        System.out.println();

        return userMigrations;
    }

    public static void createUsersSession(List<UserMigration> users) throws SQLException {
        int current = 0;

        NewDatabaseHelper.trancateTable("sessions");

        Date currentDate = new Date(Calendar.getInstance().getTime().getTime());

        System.out.print("Prepare Sessions   ");

        List<Session> sessions = new ArrayList<>();
        for (UserMigration user : users) {
            if (user.getOld_id()>0) {
                sessions.add(new Session("user_" + String.valueOf(user.getOld_id()), user.getId(), currentDate, currentDate));
                if (current % 10000 == 0) {
                    System.out.print(".");

                    NewDatabaseHelper.insertSessionToDatabase(sessions);

                    sessions.clear();
                }

                current += 1;
            }
        }

        NewDatabaseHelper.insertSessionToDatabase(sessions);

        System.out.println();
        System.out.print("Sessions prepared ");
    }
}
