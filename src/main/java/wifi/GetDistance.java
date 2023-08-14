package wifi;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.ArrayList;

public class GetDistance {
    public static void main(String[] args) {


    }
    public  ArrayList<GetDistanceDto> GetDistance(double get_lat, double get_lnt){

        ArrayList<GetDistanceDto> getDistanceDtoList = new ArrayList<>();
        Connection connection = null;
        try {
            Class.forName("org.sqlite.JDBC");
            String dbFile = "C:\\coding_dev\\sqlite-tools-win32-x86-3420000\\openAPIWIFI.db";
            connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
            Statement stat = connection.createStatement();

            String sql = "SELECT manage_number, wifi_name,address_1,address_2,lnt,lat,address_3,floor,type,install_agency,service,form,year,door,environment,date ,(6371*acos(cos(radians("+get_lat+"))*cos(radians(lat))*cos(radians(lnt)-radians("+get_lnt+"))+sin(radians("+get_lat+"))*sin(radians(lat))))AS dist FROM wifi_info ORDER BY dist ASC limit 20;";

            ResultSet resultSet = stat.executeQuery(sql);
            while (resultSet.next()){
                double dist = resultSet.getDouble("dist");
                String manage_number = resultSet.getString("manage_number");
                String wifi_name = resultSet.getString("wifi_name");
                double lnt = resultSet.getDouble("lnt");
                double lat = resultSet.getDouble("lat");
                String address_1 = resultSet.getString("address_1");
                String address_2 = resultSet.getString("address_2");
                String address_3 = resultSet.getString("address_3");
                String floor = resultSet.getString("floor");
                String type = resultSet.getString("type");
                String install_agency = resultSet.getString("install_agency");
                String service = resultSet.getString("service");
                String form = resultSet.getString("form");
                String year = resultSet.getString("year");
                String door = resultSet.getString("door");
                String environment = resultSet.getString("environment");
                String date = resultSet.getString("date");

                String dist_form = String.format("%.4f",dist);
                double final_dist = Double.parseDouble(dist_form);

                GetDistanceDto getDistanceDto = new GetDistanceDto();
                getDistanceDto.setManage_number(manage_number);
                getDistanceDto.setWifi_name(wifi_name);
                getDistanceDto.setLnt(lnt);
                getDistanceDto.setLat(lat);
                getDistanceDto.setDist(final_dist);
                getDistanceDto.setAddress_1(address_1);
                getDistanceDto.setAddress_2(address_2);
                getDistanceDto.setAddress_3(address_3);
                getDistanceDto.setFloor(floor);
                getDistanceDto.setType(type);
                getDistanceDto.setInstall_agency(install_agency);
                getDistanceDto.setService(service);
                getDistanceDto.setForm(form);
                getDistanceDto.setYear(year);
                getDistanceDto.setDoor(door);
                getDistanceDto.setEnvironment(environment);
                getDistanceDto.setDate(date);

                getDistanceDtoList.add(getDistanceDto);
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
        return getDistanceDtoList;
    }
}
