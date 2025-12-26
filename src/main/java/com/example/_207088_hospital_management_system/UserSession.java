package com.example._207088_hospital_management_system;


public class UserSession {
    private static String userId;
    private static String userName;
    private static String role;


    public static void setSession(String id, String name, String userRole) {
        userId = id;
        userName = name;
        role = userRole;
    }


    public static String getUserId() { return userId; }
    public static String getUserName() { return userName; }
    public static String getRole() { return role; }


    public static void cleanUserSession() {
        userId = null;
        userName = null;
        role = null;
    }
}
