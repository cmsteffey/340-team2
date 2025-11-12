<!DOCTYPE html>
<html>
    <head>
        <title>Deck View</title>
        <style>

        </style>
    </head>
    <body>
        <table border="1">
            <thead>
                <tr>
                    <th>Owner</th>
                    <th>Name</th>
                    <th>Colors</th>
                </tr>
            </thead>
            <tbody>
                <#list decks as deck>
                    <tr>
                        <td>${deck.getAccount().getUsername()}</td>
                        <td>${deck.nickname}</td>
                        <td>${deck.getColorsAsString()}</td>
                    </tr>
                </#list>
            </tbody>
        </table>
    </body>
</html>