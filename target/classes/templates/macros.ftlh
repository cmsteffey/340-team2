<#macro header>
    <div class="header">
        <div class="icon">
            <img id="logo" src="/static/Logo.png" width="150px">
        </div>
        <a href="/view/dashboard">Dashboard</a>
        <a href="/view/calendar">Calendar</a>

        <#if currentAccount??>
            <a class="login-link profile-link" href="/view/profile">My Account: ${currentAccount.username}</a>
        <#else>
            <a id="login-link" href="/view/login">Log In</a>
        </#if>
    </div>
</#macro>
<#macro dashbox title>
    <div class="dashbox">
        <h2 class="dashbox-title">${title}</h2>
        <svg class="dashbox-content" viewBox="0 0 200 100">
            <#list 0..10 as num>
                <line x1="0" x2="200" y1="${num * 10}" y2="${num * 10}" stroke="white" <#if num == 0 || num == 10> stroke-width="1px" <#else> stroke-width="0.5px" </#if>></line>
            </#list>
            <#list 0..99 as num>
                <line x1="${num}" y1="${num}" x2="${num + 1}" y2="${num / 2}" stroke="beige"></line>
            </#list>
        </svg>
    </div>
</#macro>