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
    </body>
</html>