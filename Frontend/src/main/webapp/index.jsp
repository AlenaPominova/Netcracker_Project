<%@ page contentType = "text/html; charset = UTF-8" %>
<html>
<head>
    <title>Welcome</title>
    <link rel="stylesheet" type="text/css" href="frontend.css">
</head>
<body>
<form action="action_page.php">
    <div class="block">
        <%--@declare id="email"--%><%--@declare id="psw"--%><%--@declare id="psw-repeat"--%><h1>Welcome to ParkinGo!</h1>
        <p>Please fill in this form to create an account.</p>

        <label for="email"><b>Email</b></label>
        <input type="text" placeholder="" name="email" required>
        <br>
        <label for="psw"><b>Password</b></label>
        <input type="text" placeholder="" name="psw" required>
        <br>
        <label for="psw-repeat"><b>Repeat Password</b></label>
        <input type="password" placeholder="" name="psw-repeat" required>

        <!--<label>
            <input type="checkbox" checked="unchecked" name="remember" style="margin-bottom:15px"> Remember me
        </label>*/-->

            <div class="clearfix">
            <button type="button" class="cancelbtn">Cancel</button>
            <button type="submit" class="signupbtn">Sign Up</button>
        </div>
    </div>
</form>
</body>
</html>