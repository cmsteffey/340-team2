<#import "macros.ftlh" as macros>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <link rel="stylesheet" href="/static/navbar.css">
        <link rel="stylesheet" href="/static/dashboard.css">
        <title>Coach Dashboard</title>
    </head>
    <body>
        <@macros.header/>
        <nav id="navbar" hidden>
            <a href="/view/dashboard">Dashboard</a>
            <a href="/view/profile">Profile</a>
            <a href="/view/calendar">Calendar</a>
        </nav>
        <main>
            <h1>Dashboard</h1>
            <button hidden id="add-block" popovertarget="add-block-menu">+</button>
            <div class="dashbox-container">
                <div class="dashbox">
                    <h2 class="dashbox-title">Week Revenue</h2>
                    <h1 class="dashbox-content">$30.89</h1>
                </div>
                <@macros.dashbox "Test"/>
                <div class="dashbox">
                    <h2 class="dashbox-title">Daily New Subscribers</h2>
                    <svg class="dashbox-content" viewBox="0 0 200 100">
                        <#list 0..10 as num>
                            <line x1="0" x2="200" y1="${num * 10}" y2="${num * 10}" stroke="white" stroke-width=".5px"></line>
                        </#list>
                        <#list 0..99 as num>
                            <line x1="${num}" y1="${num}" x2="${num + 1}" y2="${num / 2}" stroke="beige"></line>
                        </#list>
                    </svg>
                </div>
                <div class="dashbox">
                    <h2 class="dashbox-title">New Reviews</h2>
                    <h1 class="dashbox-content">4/5: Ali was exceptiona...<br>5/5: Ali was very he...</h1>
                </div>
                <div class="dashbox">
                    <h2 class="dashbox-title">Post stats</h2>
                    <h1 class="dashbox-content">Recent: 3040 likes</h1>
                </div>
                <div class="dashbox">
                    <h2 class="dashbox-title">Review Rate</h2>
                    <svg class="dashbox-content pieSvg" viewBox="0 0 100 110">
                        <circle class="outer-circle" r="50" cx="50" cy="50"></circle>
                        <circle class="inner-circle" r="25" cx="50" cy="50" stroke-dasharray="calc(3.14159 * ${pct} / 2) calc(3.14159 * ${100 - pct} / 2)" stroke-width="50"></circle>
                    </svg>
                </div>
                <div class="dashbox" hidden id="sr-block">
                    <h2 class="dashbox-title">Service Requests</h2>

                    <h1 class="dashbox-content">Anna Ericsson: Coaching<br>09/28/2025</h1>
                </div>
            </div>
        </main>
        <div popover id="add-block-menu" style="background-color: rgb(153, 191, 192)">
            <span>- Week Revenue</span><br>
            <span>- Week Revenue</span><br>
            <span>- Daily New Subscribers</span><br>
            <span>- New Reviews</span><br>
            <span>- Post Stats</span><br>
            <span>- Review Rate</span><br>
            <span id="add-sr">+ Service Requests</span>
        </div>
        <script>
            document.getElementById("add-sr").onclick = (e) => {
                document.getElementById("add-block-menu").togglePopover(false);
                document.getElementById("sr-block").hidden = false;
            }
        </script>
    </body>
</html>