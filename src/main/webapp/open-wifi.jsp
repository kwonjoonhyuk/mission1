<%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-08-13
  Time: PM 9:53
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@page import="wifi.DeleteWifi" %>
<%@ page import="wifi.GetWifiApi" %>
<html>
    <head>
        <style>
            .atag{
                text-align: center;
            }
        </style>
        <title>와이파이 정보 구하기</title>
    </head>
    <body>
        <h1 style="text-align: center"><%
            DeleteWifi deleteWifi = new DeleteWifi();
            deleteWifi.Delete();
            GetWifiApi getWifiApi = new GetWifiApi();
            out.print(getWifiApi.Insert());
        %>개가 저장 되었습니다.</h1>

        <div class="atag">
            <a style="text-align: center" href="http://localhost:8080">홈으로 돌아가기</a>
        </div>
    </body>
</html>
