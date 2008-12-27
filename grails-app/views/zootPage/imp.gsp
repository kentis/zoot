

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Import pages</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ZootPage List</g:link></span>
        </div>
        <div class="body">
            <h1>import pages</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:form action="imp" method="post" enctype="multipart/form-data">
                <div class="dialog">
									<table>
                  	<tbody>
											<tr>
												<td><input type="file" name="file"/></td>
											</tr>
										</tbody>
									</table>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Import" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
