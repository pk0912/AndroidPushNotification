<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
         pageEncoding="ISO-8859-1" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>Admin Panel</title>
	<script type="text/javascript" language="javascript">
    function DisableBackButton() {
        window.history.forward()
    }
    DisableBackButton();
    window.onload = DisableBackButton;
    window.onpageshow = function (evt) { if (evt.persisted) DisableBackButton() }
    window.onunload = function () { void (0) }
 </script>
	
    <link rel="stylesheet" type="text/css"
          href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/gcmstyle.css">
    <link href='https://fonts.googleapis.com/css?family=Spinnaker' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">

    <style>
        .button-2-notification-page{
            color: white;
            text-decoration: none;
            margin-top: 40px;
        }
        .button-2-notification-page:hover a{
            color: white;
            text-decoration: none;
        }
        .button-2-noti{
            margin-top: 32px;
            margin-left: 10px;
            padding-right: 18px;
        }
        .button-2-noti:hover a{
            color: white;
            text-decoration: none;

        }
        .glyphicon-share-alt{
            margin-right: 8px;
        }
    </style>

</head>
<body>
<%--<form action="FileRead" method="post">--%>
<%--<input name="filename" type="file" size="20">--%>

<%--<input type="submit" value="Import">--%>
<%--</form>--%>
<%--<a href="index.jsp">GO TO NOTIFICATION SENDING PAGE!!!</a>--%>
<%--<form action="adminhome.html">--%>
<%--<input type="submit" value="LOG OUT">--%>
<%--</form>--%>

<%---------------------------------------------------------------------------%>


<section>
    <div class="img-wrapper">
        <img src="bootstrap/gcm_logo.png" class="img-logo">
    </div>
    <div class="heading-wrapper">
        <h1 class="title-text">Push Notification Web Service</h1>
        <div class="sign-in-wrapper">
            <%--<span class="sign-in-to-continue">Sign in to continue</span>--%>
        </div>

    </div>
    <div class="form-wrapper form-message-container container col-lg-5 col-md-5 ">
        <form action="FileRead" method="post" class="form-message-wrapper form-horizontal">

            <input type="file" class="button" name="filename" multiple required>

            <button type="submit" class="btn btn-success col-md-4 col-md-offset-2 import-msg-button"><span
                    class="glyphicon glyphicon-import" aria-hidden="true"></span>Import
            </button>

        </form>
        <%--<span class="forgot-password"><a href="#">forgot password ?</a></span>--%>
		<%--<div class="">--%>
		<%--<a href="index.jsp">GO TO NOTIFICATION SENDING PAGE!!!</a>--%>
		<%--</div>--%>
        <form action="index.jsp" >
        <button class="btn btn-success col-md-4 button-2-noti"><a href="index.jsp" class="button-2-notification-page"><span
                class="glyphicon glyphicon-share-alt" aria-hidden="true"></span>Notification Page</a></button>
        </form>
        <form action="adminhome.html" class="logout-button-wrapper">
            <button type="submit" value="logout" onclick="DisableBackButton()" class="btn btn-danger logout-button-adminpanel col-md-offset-5">Log Out</button>
         
        </form>

    </div>
</section>

</body>
</html>