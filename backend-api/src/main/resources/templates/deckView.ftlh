<#import "macros.ftlh" as macros>
<!DOCTYPE html>
<html lang="en">
<head>
    <link rel="stylesheet" href="/static/navbar.css">
    <meta charset="UTF-8">
    <meta name="viewport" content="width=device-width, initial-scale=1.0">
    <title>${deck.nickname} - Deck View</title>
    <link rel="preconnect" href="https://fonts.googleapis.com">
    <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin>
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet">
    <style>
        body {
            background: rgb(61, 59, 48);
            font-family: 'Inter', sans-serif;
            margin: 0;
            padding: 0;
        }

        main {
            padding: 20px;
            max-width: 1400px;
            margin: 0 auto;
        }

        .deck-header {
            background: rgb(77, 80, 97);
            border-radius: 15px;
            padding: 20px;
            margin-bottom: 20px;
            color: white;
        }

        .deck-header h1 {
            margin: 0 0 10px 0;
            font-size: 28px;
        }

        .deck-info {
            display: flex;
            gap: 20px;
            font-size: 16px;
        }

        .deck-info span {
            background: rgb(92, 128, 188);
            padding: 8px 15px;
            border-radius: 8px;
        }

        .cards-container {
            background: rgb(77, 80, 97);
            border-radius: 15px;
            padding: 20px;
        }

        .cards-grid {
            display: flex;
            flex-wrap: wrap;
            gap: 15px;
        }

        .card-item {
            background: rgb(92, 128, 188);
            border-radius: 10px;
            padding: 10px;
            width: 200px;
            display: flex;
            flex-direction: column;
            transition: transform 0.2s, box-shadow 0.2s;
        }

        .card-item:hover {
            transform: translateY(-5px);
            box-shadow: 0 4px 10px rgba(0, 0, 0, 0.3);
        }

        .card-image {
            width: 100%;
            border-radius: 8px;
            aspect-ratio: 5/7;
            object-fit: cover;
            margin-bottom: 10px;
        }

        .card-details {
            display: flex;
            flex-direction: column;
            gap: 5px;
        }

        .card-name {
            font-weight: bold;
            font-size: 16px;
            color: white;
            margin: 0;
        }

        .card-info {
            display: flex;
            justify-content: space-between;
            font-size: 14px;
            color: #e0e0e0;
        }

        .card-quantity {
            background: rgb(72, 108, 168);
            padding: 4px 10px;
            border-radius: 5px;
            font-weight: bold;
            color: white;
        }

        .card-colors {
            color: #ffd700;
            font-weight: bold;
        }

        .back-button {
            display: inline-block;
            background: rgb(92, 128, 188);
            color: white;
            padding: 10px 20px;
            border-radius: 8px;
            text-decoration: none;
            margin-bottom: 20px;
            transition: background 0.2s;
        }

        .back-button:hover {
            background: rgb(72, 108, 168);
        }

        .empty-message {
            text-align: center;
            color: white;
            font-size: 18px;
            padding: 40px;
        }
    </style>
</head>
<body>
<@macros.header/>

<main>
    <a href="/view/decks" class="back-button">← Back to Decks</a>

    <div class="deck-header">
        <h1>${deck.nickname}</h1>
        <div class="deck-info">
            <span>Owner: ${deck.account.username}</span>
            <#if deck.colorsAsString?has_content>
                <span>Colors: ${deck.colorsAsString}</span>
            <#else>
                <span>Colors: Colorless</span>
            </#if>
            <#assign totalCards = 0>
            <#list cards as card>
                <#assign totalCards = totalCards + card.quantity>
            </#list>
            <span>Cards: ${totalCards}</span>
        </div>
    </div>

    <div class="cards-container">
        <#if cards?has_content>
            <div class="cards-grid">
                <#list cards as card>
                    <div class="card-item">
                        <#if card.imageUrl?? && card.imageUrl != "">
                            <img src="${card.imageUrl}" alt="${card.name}" class="card-image">
                        <#else>
                            <img src="/static/default_card.png" alt="${card.name}" class="card-image">
                        </#if>
                        <div class="card-details">
                            <p class="card-name">${card.name}</p>
                            <div class="card-info">
                                <span class="card-quantity">${card.quantity}x</span>
                                <#if card.colorIdentity?has_content>
                                    <span class="card-colors">${card.colorIdentity}</span>
                                <#else>
                                    <span class="card-colors">C</span>
                                </#if>
                            </div>
                        </div>
                    </div>
                </#list>
            </div>
        <#else>
            <p class="empty-message">No cards in this deck yet.</p>
        </#if>
    </div>
</main>

</body>
</html>