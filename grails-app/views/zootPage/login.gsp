

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Zoot login</title>         
    </head>
    <body>
			<g:if test="${flash.authenticationFailure}">
				Login failed: ${message(code:"authentication.failure."+flash.authenticationFailure.result).encodeAsHTML()}
			</g:if>
			<auth:form authAction="login" success="[controller:'zootPage', action:'list']" error="[controller:'zootPage', action:'login']">
		    User: <g:textField name="login"/><br/>
		    Password: <input type="password" name="password"/><br/>
    		<input type="submit" value="Log in"/>
			</auth:form>
    </body>
</html>
