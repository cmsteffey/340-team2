<#import "macros.ftlh" as macros>
<!DOCTYPE html>
<html lang="en">
    <head>
        <meta charset="UTF-8">
        <title>My Profile</title>
        <link rel="stylesheet" href="/static/navbar.css">
        <link rel="stylesheet" href="/static/profile.css">
    </head>
    <body>
        <@macros.header/>
        <nav id="navbar">
            <a href="/view/dashboard">Dashboard</a>
            <a href="/view/profile">Profile</a>
            <a href="/view/calendar">Calendar</a>
        </nav>
        <main>
            <h1>My Profile</h1>
            <div id="about">
                <button popovertarget="image-update" id="image-update-form-button"><img id="profile-image" src="/${(currentAccount.coachData?? && currentAccount.coachData.imageName??)?then("profile_picture/" + currentAccount.coachData.imageName, "defaultPfp.png")}" alt="Guy picture"></button>
                <div id="about-textual">
                    <h2>${currentAccount.username}</h2>
                    <p id="description" class="${(currentAccount.coachData.description??)?then("empty-description", "")}">${currentAccount.coachData.description!"<No profile description set.>"}</p>
                    <button popovertarget="description-update" id="description-form-button">
                        ${(currentAccount.coachData.description??)?then("Update description", "Add a description")}
                    </button>
                </div>
            </div>
            <br>
            <h3>My Availabilities</h3>
            <button popovertarget="add-availability">Add Availability

            </button>
            <#list availabilities as availability>
                <div>
                    ${dayNames[availability.weekday]}s from ${(availability.startTime / 3600)?string?left_pad(2, '0')}:${((availability.startTime % 3600) / 60)?string?left_pad(2, '0')}
                </div>
            </#list>

        </main>
        <div id="image-update" popover>
            <h1>Update Profile Picture</h1>
            <form enctype="multipart/form-data" action="/update-coach-pfp" method="post">
                <input type="file" name="picture" required>
                <br>
                <button>Update</button>
            </form>
        </div>
        <div id="description-update" popover>
            <h1>Update Profile Description</h1>
            <form enctype="multipart/form-data" action="/update-coach-data" method="post">
                <textarea name="description" rows="5" cols="50"></textarea>
                <br>
                <button>Update</button>
            </form>
        </div>
        <div popover id="add-availability">
            <form enctype="multipart/form-data" action="/create-availability" method="POST">
            <h1>Add an availability slot</h1>
                For <select name="weekday">
                    <#list dayNames as weekday>
                        <option value="${weekday?index}" ${(weekday == "Monday")?then(" selected", "")}>
                            ${weekday}s
                        </option>
                    </#list>
                </select><br>
                Starting at <input name="starthour" maxlength="3" type="number">:<input name="startminute" type="number" maxlength="3"> <select name="ap"><option selected value="">AM</option><option value="PM">PM</option></select><br>
                For <input name="lengthmins"> minutes<br>
                <button>Submit</button>
            </form>
        </div>
    </body>
</html>