package com.razkuuuuuuu.touragency.db;

public class SqlUtils {
    public static String formatStringValue(String value) {
        //replace ' with ` to prevent sql injection (PreparedStatement.setString doesn't replace it for some reason)
        return value.replace('\'','`');
    }
}
