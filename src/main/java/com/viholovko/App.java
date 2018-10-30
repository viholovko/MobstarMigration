package com.viholovko;

import com.viholovko.databasehelper.OperationMethhods;
import com.viholovko.model.migration.CategoryMigration;
import com.viholovko.model.migration.Post;
import com.viholovko.model.migration.UserMigration;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * @author viholovko
 */
public class App {
    private static List<UserMigration> userMigrations = new ArrayList<>();
    private static List<CategoryMigration> categories = new ArrayList<>();
    private static List<Post> posts = new ArrayList<>();

    public static void main(String[] args) {
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * *");
        try {
//            OperationMethhods.migrateFAQ();
//            System.out.println("---------------------------------------------------");
//            categories = OperationMethhods.migrateCategory();
//            System.out.println("---------------------------------------------------");
//            userMigrations = OperationMethhods.migrateUser();
//            System.out.println("---------------------------------------------------");
//            OperationMethhods.checkMemberCategory(categories, userMigrations);
//            System.out.println("---------------------------------------------------");
            categories = OperationMethhods.getAllCategories();
            userMigrations = OperationMethhods.getAllUsers();
//            OperationMethhods.addUsersSettings(userMigrations);
//            System.out.println("---------------------------------------------------");
            OperationMethhods.createUsersSession(userMigrations);
            System.out.println("---------------------------------------------------");
            OperationMethhods.migrateUserAvatar();
            System.out.println("---------------------------------------------------");
            posts = OperationMethhods.migratePosts(userMigrations, null);
            System.out.println("---------------------------------------------------");
            OperationMethhods.migrateUsersFollowers();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        System.out.println("* * * * * * * * * * * * * * * * * * * * * * * * * *");
    }
}
