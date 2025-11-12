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
                <img id="profile-image" src="/static/Guy.png" alt="Guy picture">
                <div id="about-textual">
                    <input id="ad-title" value="Ali's Expert 3-5 Color Coaching"><br>
                    <textarea id="ad-description">Providing coaching on multicolor decks, both usage and deckbuilding, with an expertise in Commander.</textarea>
                    <button id="save-ad" disabled>Save</button>
                </div>
            </div>
            <br>
            <div id="make-post">
                <h2>Make a post</h2>
                <input id="post-title" placeholder="Post Title"><br>
                <textarea id="post-content" placeholder="Post something..."></textarea><br>
                <button id="post-button">Post</button>
            </div>
            <br>
            <h2>My Posts</h2>
            <div id="posts">

            </div>
        </main>
        <script>
            document.getElementById("ad-title").addEventListener("input", e => {document.getElementById("save-ad").disabled = false;})
            document.getElementById("ad-description").addEventListener("input", e => {document.getElementById("save-ad").disabled = false;})
            document.getElementById("save-ad").addEventListener("click", e => {document.getElementById("save-ad").disabled = true;})
            document.getElementById("post-button").addEventListener("click", e => {
                let postTitle = document.getElementById("post-title").value;
                let postContent = document.getElementById("post-content").value;
                document.getElementById("post-title").value = "";
                document.getElementById("post-content").value = "";
                let postHead = document.createElement("h2");
                postHead.innerText = postTitle;
                let postBody = document.createElement("p");
                postBody.innerText = postContent;
                let postElement = document.createElement("div")
                postElement.appendChild(postHead);
                postElement.appendChild(postBody);
                document.getElementById("posts").appendChild(postElement);
            })
        </script>
    </body>
</html>