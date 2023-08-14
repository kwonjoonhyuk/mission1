package wifi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class DeleteWifi {
    public void Delete() {
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "C:\\coding_dev\\sqlite-tools-win32-x86-3420000\\openAPIWIFI.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            Statement stat = connection.createStatement();
            stat.execute("DELETE FROm wifi_info");

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (connection != null) {
                try {
                    connection.close();
                } catch (Exception e) {
                }
            }
        }
    }
}
