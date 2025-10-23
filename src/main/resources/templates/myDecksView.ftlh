<#import "macros.ftlh" as macros>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <link rel="stylesheet" href="/static/navbar.css">
    <title></title>
    <style>
        table {
            border: white;
        }
        table {
            color: white;
        }
    </style>
</head>
<body>
    <@macros.header/>
    <main>
        <table>
            <thead>
                <tr>
                    <th>
                        Deck Name
                    </th>

                    <th>
                        Deck Colors
                    </th>
                </tr>
            </thead>
            <tbody>
                <#list decks as deck>
                    <tr>
                        <td>${deck.getNickname()}</td>
                        <td>${deck.getColorsAsString()}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </main>
</body>
</html>