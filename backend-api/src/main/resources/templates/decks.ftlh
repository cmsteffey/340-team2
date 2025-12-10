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
            <form method="post" action="/view/decks/import/text" class="importForm">
                <input type="text" name="nickname" placeholder="Deck nickname" required>
                <textarea name="plainText" placeholder="Paste deck list here (e.g., '4 Lightning Bolt')" required></textarea>
                <button type="submit" class="importBtn">Import Deck</button>
            </form>

            <div class="deck-grid">
                <#list decks as deck>
                    <a href="/view/decks/${deck.id}" class="deck-card">
                        <#if deck.coverCardUUID?? && deck.coverCardUUID != "">
                            <img src="https://api.scryfall.com/cards/${deck.coverCardUUID}?format=image" alt="${deck.nickname}">
                        <#else>
                            <img src="/static/default_card.png" alt="No cover card">
                        </#if>
                        <h3>${deck.nickname}</h3>
                        <p>${deck.colorsAsString}</p>
                    </a>
                </#list>
            </div>
        </div>
    </div>
</main>

</body>
</html>