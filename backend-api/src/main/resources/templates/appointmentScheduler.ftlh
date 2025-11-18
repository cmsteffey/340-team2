<!doctype html>
<html lang="en">
<head>
    <meta charset="UTF-8">
    <meta name="viewport"
          content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
    <meta http-equiv="X-UA-Compatible" content="ie=edge">
    <title>Schedule an Appointment</title>
</head>
<body>
<form action="/schedule-appointment" enctype="multipart/form-data">
    <input name="coachId" value="${coachId}" hidden>
    <input name="time" value="${apptStart}" hidden>
    Book an appointment with ${coachName} starting at ${zonedStartTimeString}
    <label>Appointment length:<br>
        <select name="length">
            <#list 1..${maxLength / 15} as length>
                <option value="${length * 15}"${(length?is_first)?then(" selected","")}>
                    ${(length > 3)?then("${length / 4}hr ", "")}${(length % 4 != 0)?then("${length * 15}m", "")}
                </option>
            </#list>
        </select>
    </label>
    <button>Submit</button>
</form>
</body>
</html>