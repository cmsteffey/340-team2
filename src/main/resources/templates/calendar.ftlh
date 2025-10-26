<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="stylesheet" href="../navbar.css">
    <link rel="stylesheet" href="../static/calendar.css">
</head>
    <body>
        <div class="header">
            <div class="icon">
                <img id="logo" src="../Logo.png" width="150px">
            </div>
        </div>
        <nav id="navbar">
            <a href="dashboard.html">Dashboard</a>
            <a href="profile.html">Profile</a>
            <a href="calendar.html">Calendar</a>
        </nav>
        <main>
            <h1 id="calendar-header"></h1>
            <div id="calendar">

            </div>
        </main>
        <script>
            let appointments = [
            ];
            let calendar = document.getElementById('calendar');
            let now = new Date();
            let firstDayOfMonth = new Date(now.getFullYear(), now.getMonth(), 1, 0, 0, 0);
            let lastDayOfMonth = new Date(now.getFullYear(), now.getMonth() + 1, 0, 0, 0, 0);
            let lastDayOfLastMonth = new Date(now.getFullYear(), now.getMonth(), 0, 0, 0, 0);
            for(let i = 0; i < 35; ++i){
                let dayDiv = document.createElement('div');
                dayDiv.classList.add('day-box');
                dayDiv.style.gridRow = (Math.floor(i / 7) + 1) + " / " + (Math.floor(i / 7) + 2);
                dayDiv.style.gridColumn = (Math.floor(i % 7) + 1) + " / " + (Math.floor(i % 7) + 2);
                dayDiv.classList.add('row' + (Math.floor(i / 7) + 1));
                dayDiv.classList.add('col' + (Math.floor(i % 7) + 1));
                let dateNumber = i + 1 - firstDayOfMonth.getDay();
                if(dateNumber === now.getDate()){
                    dayDiv.style.border = "2px solid red";
                }
                let dateHeader = document.createElement('h2');
                dateHeader.innerText = dateNumber > lastDayOfMonth.getDate() ? (dateNumber - lastDayOfMonth.getDate()).toString() :
                    (dateNumber <= 0 ? (lastDayOfLastMonth.getDate() + dateNumber).toString() : dateNumber.toString());
                dayDiv.appendChild(dateHeader);
                if(dateNumber <= 0 || dateNumber > lastDayOfMonth.getDate()){
                    dayDiv.classList.add('wrong-month');
                }

                if(i % 7 === 4){
                    let appt = document.createElement("div");
                    appt.style.padding = "2px";
                    appt.classList.add('appointment');
                    appt.classList.add('green-appointment');
                    let apptTitle = document.createElement('span');
                    apptTitle.innerText = "Richard Smith\n12pm-12:30pm\n(Weekly)";
                    appt.appendChild(apptTitle);
                    dayDiv.appendChild(appt);

                }
                if(i === 20){
                    let appt = document.createElement("div");
                    appt.style.padding = "2px";
                    appt.classList.add('appointment');
                    appt.classList.add('blue-appointment');
                    let apptTitle = document.createElement('span');
                    apptTitle.innerText = "Ann Doe\n11am-11:30am"
                    appt.appendChild(apptTitle);
                    dayDiv.appendChild(appt);
                }
                if(i === 25){
                    let appt = document.createElement("div");
                    appt.style.padding = "2px";
                    appt.classList.add('appointment');
                    appt.classList.add('red-appointment');
                    let apptTitle = document.createElement('span');
                    apptTitle.innerText = "Mary Davis\n3pm-3:30pm"
                    appt.appendChild(apptTitle);
                    dayDiv.appendChild(appt);
                }
                if(i === 16){
                    let appt = document.createElement("div");
                    appt.style.padding = "2px";
                    appt.classList.add('appointment');
                    appt.classList.add('purple-appointment');
                    let apptTitle = document.createElement('span');
                    apptTitle.innerText = "Preston Castleberry\n1:30pm-2pm"
                    appt.appendChild(apptTitle);
                    dayDiv.appendChild(appt);
                }

                calendar.appendChild(dayDiv);

            }
            document.getElementById("calendar-header").innerText = firstDayOfMonth.toLocaleString('en-US', {month: 'long'});

        </script>
    </body>
</html>