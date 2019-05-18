<%--
  Created by IntelliJ IDEA.
  User: aleksandrsigaev
  Date: 2019-05-25
  Time: 13:33
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Items</title>
    <!-- Bootstrap CSS -->
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm" crossorigin="anonymous">
    <script src="https://code.jquery.com/jquery-3.4.0.js"
            integrity="sha256-DYZMCC8HTC+QDr5QNaIcfR7VSPtcISykd+6eSmBW5qo="
            crossorigin="anonymous"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.14.7/umd/popper.min.js"
            integrity="sha384-UO2eT0CpHqdSJQ6hJty5KVphtPhzWj9WO1clHTMGa3JDZwrnQq4sF86dIHNDz0W1"
            crossorigin="anonymous"></script>
    <script src="https://stackpath.bootstrapcdn.com/bootstrap/4.3.1/js/bootstrap.min.js"
            integrity="sha384-JjSmVgyd0p3pXB1rRibZUAYoIIy6OrQ6VrjIEaFf/nJGzIxFDsf4x0xIM+B07jRM"
            crossorigin="anonymous"></script>
</head>
<body>
<div class="container">
    <%
        Integer personId = (Integer) request.getSession().getAttribute("person_id");
    %>
    <div class="mb-5"></div>
    <div class="row">
        <div class="col-md-auto" style="padding-left: 15px">
            <label for="status">Item status:</label>
            <select class="form-control" id="status">
                <option value="all">All</option>
                <option value="open">Open</option>
                <option value="close">Done</option>
                <%
                    if (personId != null) {
                %>
                <option value="my_items">My Items</option>
                <%
                    }
                %>
            </select>
        </div>

        <div class="col-sm-6"></div>

        <div class="col mt-3">
            <form id="" action="${pageContext.servletContext.contextPath}/car_item/create" method="get">
                <button type="submit" class="btn btn-success">Add Item</button>
            </form>
        </div>

        <%
            if (personId == null) {
        %>
        <div class="col mt-3">
            <form style="margin-left: 10px" id="signup" action="${pageContext.servletContext.contextPath}/registration"
                  method="get">
                <button type="submit" class="btn btn-primary">SignUp</button>
            </form>
        </div>

        <div class="col mt-3">
            <form id="signin" action="${pageContext.servletContext.contextPath}/signIn" method="get">
                <button type="submit" class="btn btn-primary">SignIn</button>
            </form>
        </div>
        <%
        } else {
        %>
        <div class="col mt-3">
            <form id="my_page" action="${pageContext.servletContext.contextPath}/" method="get">
                <button type="submit" class="btn btn-primary">MyPage</button>
            </form>
        </div>
        <div class="col mt-3">
            <form id="logout" action="${pageContext.servletContext.contextPath}/logout" method="get">
                <button type="submit" class="btn btn-primary">Logout</button>
            </form>
        </div>

        <%
            }
        %>

    </div>

    <div class="table-responsive" style="margin-top: 20px">
        <table class="table" id="table">
            <thead>

            </thead>

            <tbody>

            </tbody>
        </table>
    </div>
</div>

<script>
    createTable("all");

    function createNewUser() {
        var item = {
            description: $('#description').val(),
            create: null
        };

        $.ajax({
            type: "POST",
            url: "./item",
            data: JSON.stringify(item),
            complete: function (data) {
            },
            dataType: 'json'
        });
    }

    function checkStatus(sold) {
        if (sold === false) {
            return "open"
        } else {
            return "close"
        }
    }

    $("#status").change(function () {
        createTable($('select#status').val());
    });

    function createTable(status) {
        $($.ajax({
            method: "GET",
            url: './find_car_item?find='.concat(status),
            dataType: 'json',
            complete: function (data) {

                $('#table thead th').remove();

                var resultHead ="";
                resultHead = resultHead.concat('<tr>',
                    '<th>#</th> ',
                    '<th>Car</th> ',
                    '<th>Description</th> ',
                    '<th>Price</th> ',
                    '<th>Create</th> ' +
                    '<th>Status</th> ');
                if (status === 'my_items') {
                    resultHead = resultHead.concat("<th>Change</th>");
                }
                resultHead = resultHead.concat('</tr> ');

                $('#table thead').append(resultHead);

                $('#table tbody tr').remove();

                var items = JSON.parse(data.responseText);
                var resultTbody = '';

                for (var i = 0; i < items.length; i++) {
                    resultTbody = resultTbody.concat(
                        '<tr>' +
                        '<td>' + i + 1 + '</td>' +
                        '<td>' + items[i].car.make + " " + items[i].car.model + '</td>' +
                        '<td>' + items[i].description + '</td>' +
                        '<td>' + items[i].price + '</td>' +
                        '<td>' + items[i].created + '</td>' +
                        '<td>' + checkStatus(items[i].sold) + '</td>'
                    );
                    if (status === 'my_items') {
                        resultTbody = resultTbody.concat('<td>');

                        resultTbody = resultTbody.concat("<form id=\"logout\" action=\"./change\" method=\"get\">" +
                            + "<button type=\"submit\" class=\"btn btn-primary\">Change</button>" +
                            + "</form>");

                        resultTbody = resultTbody.concat('</td>');
                    }
                    resultTbody = resultTbody.concat('</tr>');
                }
                $('#table tbody').append(resultTbody);
            }
        }));
    }

</script>
</body>
</html>
