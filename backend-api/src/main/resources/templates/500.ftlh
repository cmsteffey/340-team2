<#import "macros.ftlh" as macros>
<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <link rel="stylesheet" href="/static/navbar.css">
    <title>Document</title>
</head>
<body>
    <@macros.header/>
    <p style="color: var(--text-main)">${error}</p>
</body>
</html>