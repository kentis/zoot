

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
				<script src="${resource(dir: 'plugins/zoot-0.5/CodeMirror-0.66/js', file: 'codemirror.js')}" type="text/javascript"></script>
        <link rel="stylesheet" type="text/css" href="${resource(dir: 'plugins/zoot-0.5/CodeMirror-0.66/css/', file:'docs.css')}"/>
        <style type="text/css">
          .CodeMirror-line-numbers {
            width: 2.2em;
            color: #aaa;
            background-color: #eee;
            text-align: right;
            padding-right: .3em;
            font-size: 10pt;
            font-family: monospace;
            padding-top: .4em;
          }
        </style>
        <title>Create ZootPage</title>         
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ZootPage List</g:link></span>
        </div>
        <div class="body">
            <h1>Create ZootPage</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${zootPage}">
            <div class="errors">
                <g:renderErrors bean="${zootPage}" as="list" />
            </div>
            </g:hasErrors>
            <g:form action="save" method="post" >
                <div class="dialog">
									<g:render template="zootPageForm" bean="zootPage" plugin="zoot"/>
                </div>
                <div class="buttons">
                    <span class="button"><input class="save" type="submit" value="Create" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
