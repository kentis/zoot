

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>ZootPage List</title>
				<script src="http://code.jquery.com/jquery-latest.js"></script>
			  <link rel="stylesheet" href="http://dev.jquery.com/view/trunk/plugins/treeview/demo/screen.css" type="text/css" />
			  <link rel="stylesheet" href="http://dev.jquery.com/view/trunk/plugins/treeview/jquery.treeview.css" type="text/css" />
			  <script type="text/javascript" src="http://dev.jquery.com/view/trunk/plugins/treeview/jquery.treeview.js"></script>
			  <script>
				  $(document).ready(function(){
				    $("#page-list").treeview();
				  });
			  </script>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="create" action="create">New ZootPage</g:link></span>
        </div>
        <div class="body">
            <h1>ZootPage List</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="list">
								<ul id="page-list" class="page-list">
										<g:render template="zootPage_list" model="[zootPageList: zootPageList, open: true]" plugin="zoot"/>
								</ul>					
            </div>
        </div>
    </body>
</html>
