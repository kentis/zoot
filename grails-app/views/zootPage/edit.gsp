

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Edit ZootPage</title>
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
				<g:javascript library="prototype" />
				<script type="text/javascript">
					function pw() {
						var form = document.forms[0];
						var body = "";
						if("${zootPage.filter_type}" == "wysiwyg html"){
							var oEditor = FCKeditorAPI.GetInstance('body');
							body = encodeURIComponent(oEditor.GetHTML(true));
						}else {
							body= encodeURIComponent(document.getElementById('body').value);
						}
				    var params = "title="+escape(document.getElementById('title').value)+"&body="+body+"&filter_type="+document.getElementById('filter_type').value;
						new Ajax.Request('/machina_web/zootPage/render',{
							method:"post",
							asynchronous:true,
							evalScripts:true,
							parameters:params,
							onSuccess:function(transport) {
								document.getElementById('preview').update(transport.responseText);
							}
						
						});
					}
				</script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ZootPage List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ZootPage</g:link></span>
        </div>
        <div class="body">
            <h1>Edit ZootPage</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <g:hasErrors bean="${zootPage}">
            <div class="errors">
                <g:renderErrors bean="${zootPage}" as="list" />
            </div>
            </g:hasErrors>
            <g:form method="post" >
                <input type="hidden" name="id" value="${zootPage?.id}" />
                <div class="dialog">
									<g:render template="zootPageForm" bean="zootPage" plugin="zoot"/>
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
						<h2>Previous revisions</h2>
						<ul class="revisions">
							<g:each in="${zootPage.revisions}" var="revision">
								<li><g:link action="diff" controller="revisions" id="${revision.id}">${revision.toString()}</g:link></li>
							</g:each>
						</ul>
						<input type="button" value="Preview" name="preview" onclick="pw();"/>
						<object id="preview" type="text/html">
							
						</object>
        </div>
    </body>
</html>
