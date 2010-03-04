<%! import no.machina.zoot.domain.* %>

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Create ZootPage</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ZootPage List</g:link></span>
        </div>
        <div class="body">
            <h1>Reorder pages</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${zootPage}">
            <div class="errors">
                <g:renderErrors bean="${zootPage}" as="list" />
            </div>
            </g:hasErrors>
        		<ul>
							<g:each in="${ZootPage.findAllByParent(page, [order:'asc', sort: 'pos'])}" var="child" id="${page.id}">
								<h2>${child.title}</h2>
								<p>
									<g:form action="reorder" method="post" id="${page.id}" >
										<g:hiddenField name="subject" value="${child.id}" />
										<g:hiddenField name="cmd" value="up" />
										<input class="save" type="submit" value="Move up" />
									</g:form>
									<g:form action="reorder" method="post" id="${page.id}">
										<g:hiddenField name="subject" value="${child.id}" />
										<g:hiddenField name="cmd" value="down" />
										<input class="save" type="submit" value="Move down" />
									</g:form>
								</p>
								</g:each>
						</ul>
        </div>
    </body>
</html>
