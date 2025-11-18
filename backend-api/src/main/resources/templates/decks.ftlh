<#import "macros.ftlh" as macros>
<!DOCTYPE HTML>
<html lang="en">

<head>
    <link rel="stylesheet" href="/static/navbar.css">
    <link rel="stylesheet" href="/static/decks.css">

    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">

    <title>Decks</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
</head>

<body>
<@macros.header/>

<main>
    <div class="container">
        <div class="decksBox">
            <div class="importWrapper">
                <form method="post" action="/importDeck" class="importForm">
                    <input type="text" name="scryfallUrl" placeholder="Paste Scryfall deck URL" required>
                    <input type="text" name="nickname" placeholder="Deck nickname" required>
                    <button type="submit" class="importBtn">Import</button>
                </form>
            </div>

            <#list decks as deck>
                <div class="deckContainer">
                    <div class="deckImageAndTitle">
                        <h3>${deck.name}</h3>
                        <#if deck.imageName?? && deck.imageName != "">
                            <img src="../${deck.imageName}" width="90%">
                        </#if>
                    </div>
                    <p>${deck.description}</p>
                </div>
            </#list>

        </div>
    </div>
</main>

</body>
</html>
