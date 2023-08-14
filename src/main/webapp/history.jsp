<%@ page import="history.HistoryCRUD" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="history.HistoryDto" %><%--
  Created by IntelliJ IDEA.
  User: user
  Date: 2023-08-13
  Time: PM 10:04
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
    <head>
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.0/jquery.min.js"></script>
        <title>와이파이 정보 구하기</title>
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
         function delete_1() {
             if (!confirm('삭제하시겠습니까???')){
                 $('#id').val('null');
                 return false;
             }else {

             }
         }
        </script>

        <h1>와이파이 정보 구하기</h1>
        <a href="http://localhost:8080">홈</a>
        <a href="history.jsp">히스토리 목록</a>
        <a href="open-wifi.jsp">Open API 와이파이 정보가져오기</a>
        <br/>
        <br/>
        <br/>

        <%
            HistoryCRUD historyCRUD = new HistoryCRUD();
            String id = request.getParameter("id");
            if (id!=null){
                historyCRUD.Delete(id);
            }
        %>
        <table id="customers">
            <tr>
                <th style="width: 80px">ID</th>
                <th style="width: 400px">X좌표</th>
                <th style="width: 400px">Y좌표</th>
                <th>조회일자</th>
                <th style="width: 70px">비고</th>
            </tr>
            <%
                ArrayList<HistoryDto> historyDtoArrayList = historyCRUD.GetHistory();
                for (HistoryDto historyDto : historyDtoArrayList) {
            %>
            <tr>
                <td><%=historyDto.getId()%></td>
                <td><%=historyDto.getLnt()%></td>
                <td><%=historyDto.getLat()%></td>
                <td><%=historyDto.getDate()%></td>
                <td style="text-align: center">
                    <form>
                        <input hidden="hidden" name="id"  id="id" type="text" value="<%=historyDto.getId()%>">
                        <input formmethod="post" id="delete" type="submit" value="삭제" onclick="delete_1()">
                    </form>

                </td>
            </tr>
            <%
                }
            %>
        </table>

    </body>
</html>
