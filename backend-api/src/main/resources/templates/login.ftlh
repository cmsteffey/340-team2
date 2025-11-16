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
    <form action="/sessions" method="POST">
        <h1 id="login-header" style="font-family: Inter, sans-serif">Log In to MagiCoach</h1>
        <input type="email" name="email" placeholder="Email address" autocomplete="username" required><br><br>
        <input type="password" name="password" placeholder="Password" autocomplete="current-password" required><br><br>
        <input type="submit" value="Log In">
    </form>
</div>
</body>
</html>