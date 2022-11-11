package org.example;

import java.sql.SQLException;

public class JdbcMain {
    public static void main(String[] args) throws SQLException {
        DatabaseController dbController = new DatabaseController();
        dbController.createDatabase();
        dbController.addUser("Vladimir", "rtf2234");
        dbController.printUserStatistics();
        String res = dbController.getUserInformationFromId(1);
        System.out.println(res);
        String res1 = dbController.getUserInformationFromId(45);
        System.out.println(res1);
    }
}
