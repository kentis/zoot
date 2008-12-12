

<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
        <meta name="layout" content="main" />
        <title>Show ZootPage</title>
    </head>
    <body>
        <div class="nav">
            <span class="menuButton"><a class="home" href="${createLinkTo(dir:'')}">Home</a></span>
            <span class="menuButton"><g:link class="list" action="list">ZootPage List</g:link></span>
            <span class="menuButton"><g:link class="create" action="create">New ZootPage</g:link></span>
        </div>
        <div class="body">
            <h1>Show ZootPage</h1>
            <g:if test="${flash.message}">
            <div class="message">${flash.message}</div>
            </g:if>
            <div class="dialog">
                <table>
                    <tbody>

                    
                        <tr class="prop">
                            <td valign="top" class="name">Id:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'id')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Position:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'_position')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Author:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'author')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Body:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'body')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Children:</td>
                            
                            <td  valign="top" style="text-align:left;" class="value">
                                <ul>
                                <g:each var="c" in="${zootPage.children}">
                                    <li><g:link controller="zootPage" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
                                </g:each>
                                </ul>
                            </td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Date Created:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'dateCreated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Filtertype:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'filter_type')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Fullpath:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'full_path')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Keywords:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'keywords')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Last Updated:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'lastUpdated')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Parent:</td>
                            
                            <td valign="top" class="value"><g:link controller="zootPage" action="show" id="${zootPage?.parent?.id}">${zootPage?.parent?.encodeAsHTML()}</g:link></td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Slug:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'slug')}</td>
                            
                        </tr>
                    
                        <tr class="prop">
                            <td valign="top" class="name">Title:</td>
                            
                            <td valign="top" class="value">${fieldValue(bean:zootPage, field:'title')}</td>
                            
                        </tr>
                    
                    </tbody>
                </table>
            </div>
            <div class="buttons">
                <g:form>
                    <input type="hidden" name="id" value="${zootPage?.id}" />
                    <span class="button"><g:actionSubmit class="edit" value="Edit" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </g:form>
            </div>
        </div>
    </body>
</html>
