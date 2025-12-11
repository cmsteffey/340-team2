<#import "macros.ftlh" as macros>
<!DOCTYPE html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <title>Title</title>

    <link rel="stylesheet" href="../navbar.css">
    <link rel="stylesheet" href="../static/calendar.css">
</head>
    <body>
        <@macros.header/>
        <main>
            <h1 id="calendar-header">${monthName}</h1>
            <div id="calendar">
                <#list 0..6 as dateHeader>
                    <div class="day-box col${dateHeader + 1} row0"><h2>${dayNames[dateHeader]}</h2></div>
                </#list>
                <#list 0..5 as row>
                    <#list 0..6 as column>
                        <#assign offset=row*7+column-startDay/>
                        <div class="day-box row${row + 1} col${column + 1}${(offset < 0 || offset >= monthLength)?then(" wrong-month", "")}${(offset + 1 == currentDay)?then(" current", "")}">
                            <h2>
                                ${(offset < 0)?then(prevMonthLength + offset, ((offset >= monthLength)?then(
                                    offset - monthLength, offset
                                ))) + 1}
                            </h2>
                            <div class="appointment-container">
                                <#list appointments as appointment>
                                    <#if offsets[appointment?index] == offset>
                                        <div class="appointment blue-appointment">
                                            Appt: ${(currentAccount.role?string == "CUSTOMER")?then(appointment.coach.username,appointment.customer.username)}<br>
                                            ${timesToShow[appointment?index]}
                                        </div>
                                    </#if>
                                </#list>
                            </div>
                        </div>
                    </#list>
                </#list>
            </div>
        </main>
    </body>
</html>