<#import "macros.ftlh" as macros>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Login to MagiCoach</title>
    <link rel="stylesheet" href="/static/login.css">
    <link rel="stylesheet" href="/static/navbar.css">
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
</head>
<body>
<@macros.header/>
<div class="main">
    <form action="/registration" method="POST" enctype="multipart/form-data">
        <h1 id="login-header" style="font-family: Inter, sans-serif">Create an Account</h1>
        <input type="text" name="firstName" placeholder="First name" autocomplete="given-name"><br><br>
        <input type="text" name="lastName" placeholder="Last name" autocomplete="family-name"><br><br>
        <input type="text" name="username" placeholder="Username" autocomplete="username" required><br><br>
        <input type="email" name="email" placeholder="Email address" autocomplete="email" required><br><br>
        <input type="password" name="passHash" placeholder="Password" autocomplete="current-password" required><br><br>
        <label>Account type:
            <select name="role">
                <option value="COACH">Coach</option>
                <option value="CUSTOMER">Customer</option>
            </select>
        </label>
        <input type="submit" value="Register">
    </form>
</div>
</body>
</html>