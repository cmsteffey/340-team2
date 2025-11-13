<#import "macros.ftlh" as macros>
<!DOCTYPE HTML>
<html lang="en">

    <head>
        <link rel="stylesheet" href="/static/navbar.css">
        <link rel="stylesheet" href="/static/userDashboard.css">

        <meta charset="UTF-8">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">

        <title>Dashboard</title>
        <link rel="preconnect" href="https://fonts.googleapis.com"> 
        <link rel="preconnect" href="https://fonts.gstatic.com/" crossorigin>
        <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap" rel="stylesheet"> 
    </head>
    <body>
        <@macros.header/>
        <div class="header">
            <div class="icon">
                <img id="logo" src="/Logo.png" width="150px">
            </div>
            <a id="login-link" href="../view/login">Log In</a>
            <a id="dashboard-link" href="../view/dashboard">Dashboard</a>
            <a id="decks-link" href="../view/decks">Decks</a>
        </div>

        <main>
        <div class="container">
            <div class="featuredBox">
                <#list accounts as account>
                    <#if (account.role?string == "COACH")>
                        <div class="featuredCoachBox">
                            <div class="featuredCoachContentBox">
                                <img id="coach1" src="../static/Guy.png" height="160px"/>
                            </div>
                            <div class="featuredCoachContentBox">
                                <h3>${account.username}</h3>
                            </div>
                        </div>
                    </#if>
                </#list>
            </div>

            <div class="searchBox">
                <div class="searchBoxHeader">
                    <div class="searchHeaderBar">
                        <form>
                            <input type="text" id="coachSearchBar" placeholder="Search..." onkeyup="filterCoaches()">
                        </form>
                    </div>
                </div>

                <div class="searchResultsBox">
                    <div class="searchCategories">
                        <span>Name</span>
                        <span>Age</span>
                        <span>Preferred Style</span>
                    </div>
                    <div class="searchResult">
                        <span>Ali</span>
                        <span>24</span>
                        <span>Control</span>
                    </div>
                </div>
            </div>

            <div class="eventBox">
                <div class="event">
                    <div class="dateBox">
                        17
                    </div>
                    Upcoming coaching session with Ali!
                </div>
            </div>
        </div>
        </main>
    <script src="dashboard.js"></script>
    </body>
</html>