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
    <link href="https://fonts.googleapis.com/css2?family=Inter:ital,opsz,wght@0,14..32,100..900;1,14..32,100..900&display=swap"
          rel="stylesheet">
</head>

<body>
<@macros.header/>

<main>
    <div class="container">
        <div class="featuredBox">
            <#list accounts as account>
                <#if account.role?string == "COACH">
                    <div class="featuredCoachBox">
                        <div class="featuredCoachContentBox">
                            <img id="coach1"
                                 src="<#if account.coachData?? && account.coachData.imageName??>/profile_picture/${account.coachData.imageName}<#else>/static/Guy.png</#if>"
                                 height="160px"/>
                        </div>
                        <div class="featuredCoachContentBox">
                            <h3>${account.username}</h3>
                            <hr class="divider">
                            <h4>
                                <#if account.coachData??>
                                    ${account.coachData.description!""}
                                </#if>
                            </h4>
                        </div>

                        <#if subscriptions[account.id?string]!false>
                            <form method="post" action="/unsubscribe">
                                <input type="hidden" name="coachId" value="${account.id}">
                                <button type="submit" class="subscribeBtn">Unsubscribe</button>
                            </form>
                        <#else>
                            <form method="post" action="/subscribe">
                                <input type="hidden" name="coachId" value="${account.id}">
                                <button type="submit" class="subscribeBtn">Subscribe</button>
                            </form>
                        </#if>
                    </div>
                </#if>
            </#list>
        </div>

        <div class="postBox">
            <div class="postBoxHeader">
                <div class="postHeaderBar">
                    <div class="searchCategories">
                        <span>Name</span>
                        <span>Post Time</span>
                        <span>Title</span>
                        <span>Content</span>
                    </div>
                </div>
            </div>

            <div class="searchResultsBox">
                <#list coachPosts as post>
                    <div class="searchResult">
                        <span>${post.author.firstName}</span>
                        <span>${post.formattedDate}</span>
                        <span>${post.title}</span>
                        <span>${post.content}</span>
                    </div>
                </#list>
            </div>
        </div>

        <div class="eventBox">
            <#if upcomingEvents?? && upcomingEvents?has_content>
                <#list upcomingEvents as event>
                    <div class="event">
                        <div class="dateBox">
                            ${event.startTime?string("dd")}
                        </div>
                        <div class="eventDetails">
                            <strong>${event.title}</strong>
                            <#if event.description?has_content>
                                <p>${event.description}</p>
                            </#if>
                            <p>Host: ${event.eventHost.username}</p>
                            <#if event.eventAddress?has_content>
                                <p>Location: ${event.eventAddress}</p>
                            </#if>
                            <p>Duration: ${event.eventDuration} minutes</p>
                            <#if event.eventCost gt 0>
                                <p>Cost: $${event.eventCost}</p>
                            <#else>
                                <p>Free Event</p>
                            </#if>
                            <p>Time: ${event.startTime?string("MMM dd, yyyy 'at' h:mm a")}</p>
                        </div>
                    </div>
                </#list>
            <#else>
                <div class="event">
                    <p>No upcoming events at this time.</p>
                </div>
            </#if>
        </div>
    </div>
</main>
</body>
</html>