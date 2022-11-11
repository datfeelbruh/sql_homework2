package org.example;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.*;
import java.util.stream.Collectors;

public class DatabaseController {

    private final String databaseUsername = "postgres";
    private final String databaseUrl = "jdbc:postgresql://localhost:5432/jdbc_homework";
    private final String databasePassword = "root";

    private Connection connection;

    public void createDatabase() throws SQLException {
        getConnection();

        Statement statement = connection.createStatement();
        statement.execute(getFileContent("init_tables.sql"));
        statement.execute(getFileContent("fill_tables.sql"));

        connection.close();
    }

    public void  getConnection() throws SQLException {
        connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
    }

    public void addUser(String username, String password) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

        String addUser = "INSERT INTO app_user (name, password, created_at) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(addUser);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());

        ps.setString(1, username);
        ps.setString(2, password);
        ps.setTimestamp(3, timestamp);

        ps.executeUpdate();

        connection.close();
    }

    public void addPost(String text, long userId) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

        String addPost = "INSERT INTO app_user_post (text, created_at, user_id) VALUES (?, ?, ?)";
        PreparedStatement ps = connection.prepareStatement(addPost);
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());


        ps.setString(1, text);
        ps.setTimestamp(2, timestamp);
        ps.setLong(3, userId);

        ps.executeUpdate();

        connection.close();
    }

    public void addComment(String text, long postId, long userId) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

        String addComment = "INSERT INTO app_user_comment (text, post_id, user_id, created_at) VALUES (?, ?, ?, ?)";
        Timestamp timestamp = new Timestamp(System.currentTimeMillis());
        PreparedStatement ps = connection.prepareStatement(addComment);

        ps.setString(1, text);
        ps.setLong(2, postId);
        ps.setLong(3, userId);

        ps.setTimestamp(4, timestamp);

        ps.executeUpdate();

        connection.close();
    }

    public void addLike(long userId, long postId, long commentId) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);

        String addLike = "INSERT INTO app_user_like (user_id, post_id, comment_id) VALUES (?, ?, ?)";

        PreparedStatement ps = connection.prepareStatement(addLike);
        ps.setLong(1, userId);
        ps.setLong(2, postId);
        ps.setLong(3, commentId);

        ps.executeUpdate();

        connection.close();
    }

    public void printUserStatistics() throws SQLException {
        System.out.println(
                "Количество пользователей: " + getTableCount("app_user") + "\n" +
                "Количество постов: " + getTableCount("app_user_post") + "\n" +
                "Количество комментариев: " + getTableCount("app_user_comment") + "\n" +
                "Количество лайков: " + getTableCount("app_user_like")
        );
    }

    private String getTableCount(String tableName) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM " + tableName + ";");
        String tableCount = null;
        while (resultSet.next()) {
            tableCount = resultSet.getString(1);
        }

        connection.close();

        return tableCount;
    }

    public String getUserInformationFromId(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT name FROM app_user WHERE id = " + id + ";");

        String userName = "";
        while (resultSet.next()) {
            userName = resultSet.getString(1);
        }

        if (userName.isEmpty()) {
            System.out.println("Пользователь не найден");
            return "";
        }

        String userInfo = "Пользователь - " +
                userName + "\n" +
                "Дата создания - " +
                getUserCreatedAtDate(id) + "\n" +
                "Самый первый пост - " +
                getUserFirstPost(id) + "\n" +
                "Количество комментов - " +
                getUserCommentsCount(id) + "\n";

        connection.close();

        return userInfo.trim();
    }


    private String getUserCreatedAtDate(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT created_at FROM app_user WHERE id = " + id + ";");
        String createdAt = null;

        while (resultSet.next()) {
            createdAt = resultSet.getString(1);
        }

        connection.close();

        return createdAt;
    }

    private String getUserFirstPost(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT text FROM app_user_post\n" +
                "JOIN app_user ON app_user_post.user_id = app_user.id\n" +
                "WHERE app_user.id = " + id + "\n" +
                "ORDER BY app_user_post.created_at\n" +
                "LIMIT 1;");
        String firstPostStringDate = null;

        while (resultSet.next()) {
            firstPostStringDate = resultSet.getString(1);
        }

        connection.close();

        return firstPostStringDate;
    }

    private int getUserCommentsCount(long id) throws SQLException {
        Connection connection = DriverManager.getConnection(databaseUrl, databaseUsername, databasePassword);
        Statement statement = connection.createStatement();

        ResultSet resultSet = statement.executeQuery("SELECT COUNT(*) FROM app_user_comment\n" +
                "JOIN app_user ON app_user_comment.user_id = app_user.id\n" +
                "WHERE app_user.id = " + id + ";");
        int commentsCount = 0;

        while (resultSet.next()) {
            commentsCount = resultSet.getInt(1);
        }

        connection.close();

        return commentsCount;
    }

    public String getFileContent(String filename) {
        InputStream resources = JdbcMain.class.getClassLoader().getResourceAsStream(filename);
        return new BufferedReader(new InputStreamReader(resources)).lines().collect(Collectors.joining(""));
    }
}
