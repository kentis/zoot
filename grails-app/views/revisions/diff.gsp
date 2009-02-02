<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit ZootPage</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ZootPage List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ZootPage</g:link></span>
        </div>
        <div class="body">
						<p>Current page</p>
						<object class="diffobject" id="current" type="text/html" data="${createLink(controller: 'zootPage', action:'show', id: revision.zootPage.id)}">
							The current revision should be shown here.
						</object>
						<p>Revision</p>
						<object class="diffobject" id="rev" type="text/html" data="${createLink(controller: 'revisions', action:'show', id: revision.id)}">
							The selected revision should be shown here.
            </object>
						<g:form action="revert" method="post" id="${revision.id}">
							<input type="submit" value="Revert"/>
						</g:form>
        </div>
    </body>
</html>
