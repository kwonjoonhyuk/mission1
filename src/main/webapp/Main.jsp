<%@ page import="wifi.GetDistanceDto" %>
<%@ page import="wifi.GetDistance" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="history.HistoryCRUD" %>
<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" %>
<!DOCTYPE html>
<html>
    <head>
        <title>와이파이 정보 구하기</title>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <style>
            #customers {
                font-family: Arial, Helvetica, sans-serif;
                border-collapse: collapse;
                width: 100%;
            }

            #customers td, #customers th {
                border: 1px solid #ddd;
                padding: 4px;
                font-size: 75%;
                height: 60px;
                font-weight: 800;
            }

            #customers tr:nth-child(even) {
                background-color: #f2f2f2;
            }

            #customers tr:hover {
                background-color: #ddd;
            }

            #customers th {
                padding-top: 12px;
                font-size: 75%;
                padding-bottom: 12px;
                background-color: #04AA6D;
                color: white;
                text-align: center;
            }
        </style>
    </head>
    <body>

        <script>
            const getLocation = () => {
                if (navigator.geolocation) {
                    navigator.geolocation.getCurrentPosition(
                        (position) => {
                            const time = new Date(position.timestamp);
                            console.log(position);
                            console.log(`현재시간 :` + time);
                            console.log(`latitude 위도 :` + position.coords.latitude);
                            console.log(`longitude 경도 :` + position.coords.longitude);
                            $('#latitude').val(position.coords.latitude);
                            $('#longitude').val(position.coords.longitude);
                        },
                        (error) => {
                            console.error(error);
                        },
                        {
                            enableHighAccuracy: false,
                            maximumAge: 0,
                            timeout: Infinity,
                        }
                    );
                } else {
                    alert("GPS를 지원하지 않습니다");
                }
            };
        </script>
        <h1>와이파이 정보 구하기</h1>
        <a href="http://localhost:8080">홈</a>
        <a href="history.jsp">히스토리 목록</a>
        <a href="open-wifi.jsp">Open API 와이파이 정보가져오기</a>
        <br/>
        <br/>



        <form action="http://localhost:8080" method="get">
            LAT: <input type="text" id="latitude" name="latitude" value="0.0">
            ,LNT: <input type="text" id="longitude" name="longitude" value="0.0">
            <input type="button" id="mylocation" name="mylocation" value="내 위치 가져오기" onclick="getLocation()">
            <input formmethod="get" type="submit" value="근처 WIPI 정보 보기">
        </form>
        <table id="customers">
            <tr>
                <th width="10px">거리(Km)</th>
                <th width="30px">관리번호</th>
                <th width="30px">자치구</th>
                <th width="30px">와이파이명</th>
                <th width="30px">도로명주소</th>
                <th width="30px">상세주소</th>
                <th width="50px">설치위치(층)</th>
                <th width="30px">설치유형</th>
                <th width="30px">설치기관</th>
                <th width="30px">서비스구분</th>
                <th width="30px">망종류</th>
                <th width="30px">설치년도</th>
                <th width="50px">실내외구분</th>
                <th width="50px">WIFI접속환경</th>
                <th width="30px">X좌표</th>
                <th width="30px">Y좌표</th>
                <th width="30px">작업일자</th>
            </tr>
            <%
                String lat_s = request.getParameter("latitude");
                String lnt_s = request.getParameter("longitude");
                if (lat_s!=null){

                }

                if (lat_s != null) {
                    double lat = Double.valueOf(lat_s).doubleValue();
                    double lnt = Double.valueOf(lnt_s).doubleValue();

                    HistoryCRUD historyCRUD = new HistoryCRUD();
                    historyCRUD.insert(lat,lnt);

                    GetDistance getDistance = new GetDistance();
                    ArrayList<GetDistanceDto> getDistanceDtoArrayList = getDistance.GetDistance(lat, lnt);

                    for (GetDistanceDto getDistanceDto : getDistanceDtoArrayList) {
            %>
            <tr>
                <td><%=getDistanceDto.getDist()%>
                </td>
                <td width="4px"><%=getDistanceDto.getManage_number()%>
                </td>
                <td width="4px"><%=getDistanceDto.getAddress_1()%>
                </td>
                <td width="4px"><%=getDistanceDto.getWifi_name()%>
                </td>
                <td width="4px"><%=getDistanceDto.getAddress_2()%>
                </td>
                <td width="4px"><%=getDistanceDto.getAddress_3()%>
                </td>
                <td width="4px"><%=getDistanceDto.getFloor()%>
                </td>
                <td width="4px"><%=getDistanceDto.getType()%>
                </td>
                <td width="4px"><%=getDistanceDto.getInstall_agency()%>
                </td>
                <td width="4px"><%=getDistanceDto.getService()%>
                </td>
                <td width="4px"><%=getDistanceDto.getForm()%>
                </td>
                <td width="4px"><%=getDistanceDto.getYear()%>
                </td>
                <td width="4px"><%=getDistanceDto.getDoor()%>
                </td>
                <td width="4px"><%=getDistanceDto.getEnvironment()%>
                </td>
                <td width="4px"><%=getDistanceDto.getLnt()%>
                </td>
                <td width="4px"><%=getDistanceDto.getLat()%>
                </td>
                <td width="4px"><%=getDistanceDto.getDate()%>
                </td>

                <%
                    }
                } else {
                %>
                <td colspan="18" height="200px" style="text-align: center">
                    위치 정보를 입력한 후에 조회해주세요
                </td>
                <%
                    }%>
            </tr>
        </table>
    </body>
</html>