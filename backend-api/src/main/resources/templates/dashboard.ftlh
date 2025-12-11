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
                    <h2 class="dashbox-title">Daily New Subscribers</h2>
                    <svg class="dashbox-content" viewBox="0 0 200 130">
                        <#list 0..10 as num>
                            <text x="0" y="${num * 10 + 12}" font-size="8px" fill="white"> ${10 - num}</text>
                            <line x1="20" x2="200" y1="${num * 10 + 10}" y2="${num * 10 + 10}" stroke="white" stroke-width=".5px"></line>
                        </#list>
                        <#list subscribersPerDay as subCount>
                            <circle r="4" cx="${200 - subCount?index * 30}" cy="${110 - subCount * 10}" fill="red">
                                <title>${subCount?index} day${(subCount?index == 1)?then("","s")} ago, ${subCount} new subscriber${(subCount == 1)?then("", "s")}</title>
                            </circle>
                            <#if subCount?index == 6><#break></#if>
                            <line x1="${200 - subCount?index * 30}" y1="${110 - subCount * 10}" x2="${200 - (subCount?index + 1) * 30}" y2="${110 - subscribersPerDay[subCount?index + 1] * 10}" stroke="red" stroke-width="2px"></line>
                        </#list>
                    </svg>
                </div>
                <div class="dashbox">
                    <h2 class="dashbox-title">New Reviews</h2>
                    <div class="dashbox-content">
                        <#list reviews as review>
                            <h3>${review.customer.username}<br>${review.rating}/5: ${(review.content?? && review.content?length > 0)?then(review.content, "")}</h3>
                        </#list>
                    </div>
                </div>
                <div class="dashbox">
                    <h2 class="dashbox-title">Recent comments</h2>
                    <div class="dashbox-content">
                        <#list comments as comment>
                            <h3>On "${comment.post.title}":<br>${comment.content}</h3>
                        </#list>
                    </div>
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
            <div id="creatable-container">
                <div class="post-container">
                    <h3>My Posts</h3>
                    <button popovertarget="post-creation-menu">Make a Post</button>
                    <#if posts?size == 0>
                        No posts!
                    <#else>
                        <#list posts as post>
                            <div class="dashboard-post">
                                <h3>${post.title}</h3>
                                <h5>${post.createdAt?datetime("yyyy-MM-dd'T'HH:mm:ss.SSSSSS'Z'")?string}</h5>
                                <span>${post.content}</span>
                            </div>
                            <#sep><br></#sep>
                        </#list>
                    </#if>
                </div>
                <div class="event-container">
                    <h3>My Events</h3>
                    <button popovertarget="event-creation-menu">Make an Event</button>
                    <#if events?size == 0>
                        No events!
                    <#else>
                        <#list events as event>
                            <div class="dashboard-event">
                                <h3>${event.title}</h3>
                                <h5>${event.startTime?datetime("yyyy-MM-dd'T'HH:mm:ss'Z'")?string} at ${event.eventAddress} for ${(event.eventDuration > 3600)?then((event.eventDuration / 3600)?floor + " hour", "")}${(event.eventDuration > 7200)?then("s", "")}${(event.eventDuration > 3600 && event.eventDuration % 3600 >= 60)?then(", ", "")}${(event.eventDuration % 3600 >= 60)?then(((event.eventDuration % 3600) / 60)?floor + " minute", "")}${(event.eventDuration % 3600 >= 120)?then("s", "")}</h5>
                                <span>${event.description}</span>
                            </div>
                            <#sep><br></#sep>
                        </#list>
                    </#if>
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
        <div popover id="post-creation-menu">
            <h1>Make a Post</h1>
            <form method="POST" action="/create-post">
                <h3>Title</h3><input name="title">
                <h3>Content</h3><textarea name="content" rows="15" cols="150"></textarea><br>
                <button>Post!</button>
            </form>
        </div>
        <div popover id="event-creation-menu">
            <h1>Host an Event</h1>
            <form method="POST" action="/create-event">
                <h3>Date</h3><input required type="date" id="event-date" name="date">
                <h3>Time</h3><input required type="time" id="event-time" name="time">
                <h3>Duration</h3><input required type="number" id="event-duration-hours" name="hours"> <span style="color: var(--text-main)">hours,</span> <input required type="number" id="event-duration-minutes" name="minutes"> <span style="color: var(--text-main)">minutes</span>
                <h3>Price</h3><span style="color: var(--text-main)">$</span><input required type="text" pattern="^(?<dollars>\d+)(\.(?<cents>\d{1,2}))?$" id="event-price" name="price">
                <h3>Address</h3><input required type="text" name="address">
                <h3>Title</h3><input required type="text" name="title">
                <h3>Description</h3><textarea required name="description" cols="100" rows="20"></textarea>
                <button>Create!</button>
            </form>
        </div>
    </body>
</html>