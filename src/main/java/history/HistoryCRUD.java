package history;

import wifi.GetDistanceDto;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class HistoryCRUD {

    public static void main(String[] args) {

    }
    public void insert(double lat , double lnt){
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "C:\\coding_dev\\sqlite-tools-win32-x86-3420000\\openAPIWIFI.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            Statement stat = connection.createStatement();
            stat.executeUpdate("INSERT INTO HistoryTB (history_lat,history_lnt,Insert_Date) VALUES ('"+lat+"','"+lnt+"',DATETIME('now','localtime'))");
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

    public ArrayList<HistoryDto> GetHistory(){
        ArrayList<HistoryDto> historyDtoArrayList = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "C:\\coding_dev\\sqlite-tools-win32-x86-3420000\\openAPIWIFI.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            Statement stat = connection.createStatement();
            String sql ="SELECT * FROM HistoryTB ORDER BY id DESC";
            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()){
                HistoryDto historyDto = new HistoryDto();
                String id = resultSet.getString("id");
                double history_lat = resultSet.getDouble("history_lat");
                double history_lnt = resultSet.getDouble("history_lnt");
                String Insert_Date = resultSet.getString("Insert_Date");

                historyDto.setId(id);
                historyDto.setLat(history_lat);
                historyDto.setLnt(history_lnt);
                historyDto.setDate(Insert_Date);
                historyDtoArrayList.add(historyDto);
            }
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
        return historyDtoArrayList;
    }

    public void Delete(String id){
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "C:\\coding_dev\\sqlite-tools-win32-x86-3420000\\openAPIWIFI.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            Statement stat = connection.createStatement();
            stat.execute("DELETE FROM HistoryTB WHERE id = '"+id+"'");

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
