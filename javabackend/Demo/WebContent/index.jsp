<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<%
    String pushStatus = "";
    Object pushStatusObj = request.getAttribute("pushStatus");

    if (pushStatusObj != null) {
        pushStatus = pushStatusObj.toString();
    }
%>
<head>
    <link rel="stylesheet" type="text/css"
          href="bootstrap/css/bootstrap.min.css">
    <link rel="stylesheet" type="text/css" href="bootstrap/css/gcmstyle.css">
    <link href='https://fonts.googleapis.com/css?family=Spinnaker' rel='stylesheet' type='text/css'>
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/font-awesome/4.5.0/css/font-awesome.min.css">
	<script type="text/javascript" language="javascript">
    function DisableBackButton() {
        window.history.forward()
    }
    DisableBackButton();
    window.onload = DisableBackButton;
    window.onpageshow = function (evt) { if (evt.persisted) DisableBackButton() }
    window.onunload = function () { void (0) }
 </script>
	
    <title>Google Cloud Messaging (GCM) Server</title>
</head>
<body>

<%--<h1>Google Cloud Messaging (GCM) Server in Java</h1>--%>

<%--<form action="GCMNotificationNew" method="post">--%>

<%--&lt;%&ndash;<div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<textarea rows="2" name="message" cols="23"&ndash;%&gt;--%>
<%--&lt;%&ndash;placeholder="Message to transmit via GCM"></textarea>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<div>&ndash;%&gt;--%>
<%--&lt;%&ndash;<input type="submit" value="Send Push Notification via GCM"/>&ndash;%&gt;--%>
<%--&lt;%&ndash;</div>&ndash;%&gt;--%>
<%--</form>--%>
<%--<p>--%>
<%--<h3>--%>
<%--<%=pushStatus%>--%>
<%--</h3>--%>
<%--</p>--%>
<%--<form action="adminhome.html">--%>
<%--<input type="submit" value="LOG OUT">--%>
<%--</form>--%>
<%--------------------------------------------------------------------%>

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
        <form action="GCMNotificationNew" method="post" class="form-message-wrapper form-horizontal">

                <textarea class="form-control noresize" rows="4" name="message" placeholder="Your Message" required></textarea>
            <button type="submit" class="btn btn-success col-md-4 col-md-offset-8 send-msg-button"><span class="glyphicon glyphicon-send" aria-hidden="true"></span>Send Message</button>

        </form>
        <%--<span class="forgot-password"><a href="#">forgot password ?</a></span>--%>
        <p>
        <h3>
            <%=pushStatus%>
        </h3>
        </p>
        <form action="adminhome.html" class="logout-button-wrapper">
            <button type="submit" value="logout" onclick="DisableBackButton()" class="btn btn-danger logout-button col-md-offset-5">Log Out</button>

        </form>

    </div>
</section>

<%--<section>--%>
<%--<div class="footer-links">--%>
<%--hi--%>
<%--</div>--%>
<%--</section>--%>


<%--form clear code--%>



<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
<script src="bootstrap/js/bootstrap.min.js"></script>
<%--<script>--%>
    <%--$(".hasclear").keyup(function () {--%>
        <%--var t = $(this);--%>
        <%--t.next('span').toggle(Boolean(t.val()));--%>
    <%--});--%>

    <%--$(".clearer").hide($(this).prev('input').val());--%>

    <%--$(".clearer").click(function () {--%>
        <%--$(this).prev('input').val('').focus();--%>
        <%--$(this).hide();--%>
    <%--});--%>
<%--</script>--%>


</body>
</html>
