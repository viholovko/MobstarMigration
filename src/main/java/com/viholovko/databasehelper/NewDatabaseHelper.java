package com.viholovko.databasehelper;

import com.viholovko.databaseconnection.SingletonNewDBConnection;
import com.viholovko.databaseconnection.SingletonOldDBConnection;
import com.viholovko.model.migration.*;
import com.viholovko.model.old.Category;
import com.viholovko.model.old.Entrie;

import java.sql.*;
import java.util.List;

public class NewDatabaseHelper {

    public static boolean deleteByRequest(String request) {
        int affectedrows = 0;

        try {
            Connection conn = SingletonNewDBConnection.getInstance().getConnection();
            PreparedStatement pstmt = conn.prepareStatement(request);

            affectedrows = pstmt.executeUpdate();
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
        }
        return affectedrows>0;
    }

    public static ResultSet getResultByRequest(String request) {

        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        try {
            conn = SingletonNewDBConnection.getInstance().getConnection();

            stmt = conn.createStatement();
            return stmt.executeQuery(request);
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (conn != null) try {
                conn.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return rs;
    }

    public static void insertFAQToDatabase(List<FAQMigration> faqMigrationList) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (FAQMigration faqMigration : faqMigrationList) {
                PreparedStatement pstmt = c.prepareStatement("INSERT INTO frequently_asked_questions (title,description,content,user_id, old_id, created_at, updated_at) VALUES (?,?,?,?,?,?,?)");
                pstmt.setString(1, faqMigration.getTitle());
                pstmt.setString(2, faqMigration.getDescription());
                pstmt.setString(3, faqMigration.getContent());
                pstmt.setInt(4, faqMigration.getUser_id());
                pstmt.setInt(5, faqMigration.getOld_id());
                pstmt.setDate(6, faqMigration.getCreated_at());
                pstmt.setDate(7, faqMigration.getUpdated_at());
                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void insertUserToDatabase(List<UserMigration> userMigrations) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (UserMigration user : userMigrations) {
                PreparedStatement pstmt = c.prepareStatement("INSERT INTO users (encrypted_password, full_name, display_name," +
                        "bio, birthdate, created_at, updated_at, social_id, social_type, role_id, email, confirmed, salt, old_id) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, user.getEncrypted_password());
                pstmt.setString(2, user.getFull_name());
                pstmt.setString(3, user.getDisplay_name());
                pstmt.setString(4, user.getBio());
                pstmt.setDate(5, user.getBirthdate());
                pstmt.setDate(6, user.getCreated_at());
                pstmt.setDate(7, user.getUpdated_at());
                pstmt.setString(8, user.getSocial_id());
                pstmt.setString(9, user.getSocial_type());
                pstmt.setInt(10, user.getRole_id());
                pstmt.setString(11, user.getEmail());
                pstmt.setBoolean(12, user.isConfirmed());
                pstmt.setString(13, "10");
                pstmt.setInt(14, user.getOld_id());

                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void insertSessionToDatabase(List<Session> sessions) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (Session session : sessions) {
                PreparedStatement pstmt = c.prepareStatement("INSERT INTO sessions (token, user_id, created_at, updated_at) VALUES (?,?,?,?);");
                pstmt.setString(1, session.getToken());
                pstmt.setInt(2, session.getUser_id());
                pstmt.setDate(3, session.getCreated());
                pstmt.setDate(4, session.getUpdated());

                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void insertUserSettingsToDatabase(List<UserSetting> userSettings) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (UserSetting userSetting : userSettings) {
                PreparedStatement pstmt = c.prepareStatement("INSERT INTO settings (user_id, messages_notification, friend_requests_notification," +
                        "comments_notification, likes_notification, taggs_notification, system_notification, block_user_notification," +
                        "posts_notification, system_of_units, language, created_at, updated_at) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setInt(1, userSetting.getUser_id());
                pstmt.setBoolean(2, userSetting.isMessages_notification());
                pstmt.setBoolean(3, userSetting.isFriend_requests_notification());
                pstmt.setBoolean(4, userSetting.isComments_notification());
                pstmt.setBoolean(5, userSetting.isLikes_notification());
                pstmt.setBoolean(6, userSetting.isTaggs_notification());
                pstmt.setBoolean(7, userSetting.isSystem_notification());
                pstmt.setBoolean(8, userSetting.isBlock_user_notification());
                pstmt.setBoolean(9, userSetting.isPosts_notification());
                pstmt.setInt(10, userSetting.getSystem_of_units());
                pstmt.setString(11, userSetting.getLanguage());
                pstmt.setDate(12, userSetting.getCreated_at());
                pstmt.setDate(13, userSetting.getUpdated_at());

                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void updateUserCategoryInDatabase(List<UserMigration> userMigrations) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (UserMigration user : userMigrations) {
                String SQL = "UPDATE users SET category_id = ?, role_id = ? WHERE id = ?";

                PreparedStatement pstmt = c.prepareStatement(SQL);
                pstmt.setInt(1, user.getCategory_id());
                pstmt.setInt(2, user.getRole_id());
                pstmt.setInt(3, user.getId());

                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static void updatePostInDatabase(List<Entrie> posts) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (Entrie entrie : posts) {
                String SQL = "UPDATE posts SET created_at = ?, updated_at = ? WHERE old_id = ?";

                PreparedStatement pstmt = c.prepareStatement(SQL);
                pstmt.setDate(1, entrie.getCreated_at());
                pstmt.setDate(2, entrie.getUpdated_at());
                pstmt.setInt(3, entrie.getId());

                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }


    public static void updateCategoryInDatabase(List<Category> categories) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (Category category : categories) {
                PreparedStatement pstmt = c.prepareStatement("UPDATE categories SET created_at = ?, updated_at = ? WHERE old_id = ?");
                pstmt.setDate(1, category.getCreated_at());
                pstmt.setDate(2, category.getUpdated_at());
                pstmt.setInt(3, category.getId());
                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }

    public static int trancateTable(String tableName) {

        Connection connection = null;
        int result = 0;
        try {
            connection = SingletonNewDBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            result = statement.executeUpdate("TRUNCATE " + tableName + " RESTART IDENTITY CASCADE");
            connection.commit();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static int getCountInTable(String tableName) {
        Connection conn = null;
        Statement stmt = null;
        ResultSet rs = null;
        int count = 0;
        try {
            conn = SingletonNewDBConnection.getInstance().getConnection();
            stmt = conn.createStatement();

            String query = "SELECT COUNT(*) FROM "+ tableName;
            rs =stmt.executeQuery(query);
            //Extact result from ResultSet rs
            while(rs.next()){
                count = rs.getInt(1);
            }
            rs.close();
            return count;

        } catch(SQLException s){
            s.printStackTrace();
        }
        // close Connection and Statement

        return count;
    }

    public static int countInTable(String request) {

        Connection connection = null;
        int result = 0;
        try {
            connection = SingletonNewDBConnection.getInstance().getConnection();
            connection.setAutoCommit(false);
            Statement statement = connection.createStatement();
            result = statement.executeUpdate("TRUNCATE " + request + " RESTART IDENTITY CASCADE");
            connection.commit();

            return result;
        } catch (SQLException e) {
            e.printStackTrace();
        } finally {
            if (connection != null) try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        return result;
    }

    public static void insertRelationToDatabase(List<Relation> relations) {
        Connection c;
        try {
            c = SingletonNewDBConnection.getInstance().getConnection();

            for (Relation relation : relations) {
                PreparedStatement pstmt = c.prepareStatement("INSERT INTO relationships (follower_id,followed_id,confirmed, created_at, updated_at) VALUES (?,?,?,?,?)");
                pstmt.setInt(1, relation.getFollower_id());
                pstmt.setInt(2, relation.getFollowed_id());
                pstmt.setBoolean(3, relation.isConfirmed());
                pstmt.setDate(4, relation.getCreated_at());
                pstmt.setDate(5, relation.getUpdated_at());
                pstmt.executeUpdate();
            }

        } catch (Exception e) {
            System.err.println(e.getClass().getName() + ": " + e.getMessage());
            System.exit(0);
        }
    }
}
