<!DOCTYPE html>
<html>
<head>
    <meta name="layout" content="main" />
    <g:set var="entityName" value="${message(code: 'annonce.label', default: 'Annonce')}" />
    <title><g:message code="default.list.label" args="[entityName]" /></title>
</head>
<body>
<a href="#list-annonce" class="skip" tabindex="-1"><g:message code="default.link.skip.label"
                                                              default="Skip to content&hellip;"/></a>

<div class="nav" role="navigation">
    <ul>
        <li><a class="home" href="${createLink(uri: '/')}"><g:message code="default.home.label"/></a></li>
        <li><g:link class="create" action="create"><g:message code="default.new.label"
                                                              args="[entityName]"/></g:link></li>
    </ul>
</div>

<div id="list-annonce" class="content scaffold-list" role="main">
    <h1><g:message code="default.list.label" args="[entityName]"/></h1>
    <g:if test="${flash.message}">
        <div class="message" role="status">${flash.message}</div>
    </g:if>
    <table>
        <thead>
        <tr>
            <th class="sortable"><a href="/user/index.gsp?sort=username&amp;max=10&amp;order=asc">Title</a></th>
            <th class="sortable"><a href="/user/index.gsp?sort=username&amp;max=10&amp;order=asc">Description</a></th>
            <th class="sortable"><a href="/user/index.gsp?sort=username&amp;max=10&amp;order=asc">Valid Till</a></th>
            <th class="sortable"><a href="/user/index.gsp?sort=username&amp;max=10&amp;order=asc">Illustration</a></th>
            <th class="sortable"><a href="/user/index.gsp?sort=username&amp;max=10&amp;order=asc">State</a></th>
            <th class="sortable"><a href="/user/index.gsp?sort=username&amp;max=10&amp;order=asc">Author</a></th>
        </tr>
        </thead>
        <g:each in="${annonceList}" var="instance">
            <tr>
                <td><g:link controller="annonce" action="show" id="${instance.id}">${instance.title}</g:link></td>
                <td>${instance.description}</td>
                <td>${instance.validTill}</td>
                <td>
                    <ul>
                        <g:each in="${instance.illustrations}" var="illustration">
                            <g:link controller="annonce" action="show" id="${illustration.id}">
                                <img src="http://localhost:8080/assets/${illustration.filename}"
                                     style="height: 50px; width: 50px;"/>
                            </g:link>
                        </g:each>
                    </ul>
                </td>
                <td>${instance.state}</td>
                <td><g:link controller="user" action="show"
                            id="${instance.author.id}">${instance.author.username}</g:link></td>
            </tr>
        </g:each>
    </table>

    <div class="pagination">
        <g:paginate total="${annonceCount ?: 0}"/>
    </div>
</div>
</body>
</html>