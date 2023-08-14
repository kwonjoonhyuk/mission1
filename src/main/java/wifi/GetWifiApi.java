package wifi;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

public class GetWifiApi {

    static final String KEY = "667072616a6a6b613436496f764545";
    static int TOTALCNT;
    public  int TotalCnt() throws ParseException {
        URL url = null;
        HttpURLConnection con = null;
        JSONObject result = null;
        StringBuilder sb = new StringBuilder();
        int start = 1;
        int end = 1;
        String baseUrl = "http://openapi.seoul.go.kr:8088/" + KEY + "/" +
                "json/TbPublicWifiInfo/" + start + "/" + end + "/";

        try {
            url = new URL(baseUrl);
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Content-type", "application/json");
            con.setDoOutput(true);
            BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
            while (br.ready()) {
                sb.append(br.readLine());
            }
            con.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        result = (JSONObject) new JSONParser().parse(sb.toString());
        StringBuilder out = new StringBuilder();
        JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
        int totalGet = Integer.parseInt(data.get("list_total_count").toString());
        return totalGet;
    }

    public  int  Insert() throws ParseException {
        int start = 0;
        int end = 0;
        int total = 0;

        TOTALCNT = TotalCnt();
        int pageEnd = TOTALCNT / 1000;
        int pageEndRemain = TOTALCNT % 1000;

        for (int k = 0; k <= pageEnd; k++) {
            start = (int) Math.pow(10, 3) * k + 1;

            if (k == pageEnd) {
                end = start + pageEndRemain - 1;
            } else {
                end = (int) Math.pow(10, 3) * (k + 1);
            }

            String baseUrl = "http://openapi.seoul.go.kr:8088/" + KEY + "/" +
                    "json/TbPublicWifiInfo/";

            baseUrl = baseUrl + start + "/" + end + "/";

            URL url = null;
            HttpURLConnection con = null;
            JSONObject result = null;
            StringBuilder sb = new StringBuilder();


            try {
                url = new URL(baseUrl);
                con = (HttpURLConnection) url.openConnection();
                con.setRequestMethod("GET");
                con.setRequestProperty("Content-type", "application/json");
                con.setDoOutput(true);


                BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "UTF-8"));
                while (br.ready()) {
                    sb.append(br.readLine());
                }
                con.disconnect();
            } catch (Exception e) {
                e.printStackTrace();
            }

            result = (JSONObject) new JSONParser().parse(sb.toString());

            JSONObject data = (JSONObject) result.get("TbPublicWifiInfo");
            JSONArray array = (JSONArray) data.get("row");

            JSONObject tmp;

            Connection connection = null;
            try {
                Class.forName("org.sqlite.JDBC");
                String dbFile = "C:\\coding_dev\\sqlite-tools-win32-x86-3420000\\openAPIWIFI.db";
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
                Statement stat = connection.createStatement();

                for (int i = 0; i < array.size(); i++) {
                    tmp = (JSONObject) array.get(i);
                    String 관리번호 = (String) tmp.get("X_SWIFI_MGR_NO");
                    String 자치구 = (String) tmp.get("X_SWIFI_WRDOFC");
                    String 와이파이명 = (String) tmp.get("X_SWIFI_MAIN_NM");
                    String 도로명주소 = (String) tmp.get("X_SWIFI_ADRES1");
                    String 상세주소 = (String) tmp.get("X_SWIFI_ADRES2");
                    String 설치위치 = (String) tmp.get("X_SWIFI_INSTL_FLOOR");
                    String 설치유형 = (String) tmp.get("X_SWIFI_INSTL_TY");
                    String 설치기관 = (String) tmp.get("X_SWIFI_INSTL_MBY");
                    String 서비스구분 = (String) tmp.get("X_SWIFI_SVC_SE");
                    String 망종류 = (String) tmp.get("X_SWIFI_CMCWR");
                    String 설치년도 = (String) tmp.get("X_SWIFI_CNSTC_YEAR");
                    String 실내외구분 = (String) tmp.get("X_SWIFI_INOUT_DOOR");
                    String wifi접속환경 = (String) tmp.get("X_SWIFI_REMARS3");
                    String x좌표 = (String) tmp.get("LAT");
                    double x좌표_int = Double.parseDouble(x좌표);
                    String y좌표 = (String) tmp.get("LNT");
                    double y좌표_int = Double.parseDouble(y좌표);
                    String 작업일자 = (String) tmp.get("WORK_DTTM");
                    도로명주소 = 도로명주소.replaceAll("\'","");
                    설치유형 = 설치유형.replaceAll("\'","");
                    상세주소 = 상세주소.replaceAll("\'","");

                    stat.executeUpdate("INSERT INTO wifi_info (manage_number,address_1,wifi_name,address_2," +
                            "address_3,floor,type,install_agency,service,form,year,door,environment,lat,lnt,date) VALUES ('"
                            +관리번호 + "','"
                            +자치구 + "','"
                            +와이파이명 + "','"
                            +도로명주소 + "','"
                            +상세주소 + "','"
                            +설치위치 + "','"
                            +설치유형 + "','"
                            +설치기관 + "','"
                            +서비스구분 + "','"
                            +망종류 + "','"
                            +설치년도 + "','"
                            +실내외구분 + "','"
                            +wifi접속환경 + "','"
                            +x좌표_int + "','"
                            +y좌표_int + "','"
                            +작업일자 + "')");
                    total++;
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

        }
        return total;
    }
}
