<%@ page contentType="text/html;charset=UTF-8" %>
<!DOCTYPE html>
<html>
<head>
    <title>Không có quyền truy cập</title>

    <style>
        body{
            font-family: Arial, sans-serif;
            display:flex;
            justify-content:center;
            align-items:center;
            height:100vh;
            background:#f5f5f5;
        }

        .box{
            background:white;
            padding:30px;
            border-radius:10px;
            text-align:center;
            box-shadow:0 2px 10px rgba(0,0,0,.15);
        }

        h1{
            color:#dc3545;
        }

        a{
            display:inline-block;
            margin-top:15px;
            text-decoration:none;
            padding:10px 20px;
            background:#007bff;
            color:white;
            border-radius:5px;
        }
    </style>
</head>
<body>

<div class="box">
    <h1>🚫 Truy cập bị từ chối</h1>

    <p>${errorMessage}</p>

    <a href="${pageContext.request.contextPath}/admin/dashboard">
        Quay lại Dashboard
    </a>
</div>

</body>
</html>