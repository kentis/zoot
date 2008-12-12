

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
<!--                    <table>
                        <tbody>
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="_position">Position:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'_position','errors')}">
                                    <input type="text" id="_position" name="_position" value="${fieldValue(bean:zootPage,field:'_position')}" />
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="author">Author:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'author','errors')}">
                                    <input type="text" id="author" name="author" value="${fieldValue(bean:zootPage,field:'author')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="body">Body:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'body','errors')}">
                                    <input type="text" id="body" name="body" value="${fieldValue(bean:zootPage,field:'body')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="children">Children:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'children','errors')}">
                                    
<ul>
<g:each var="c" in="${zootPage?.children?}">
    <li><g:link controller="zootPage" action="show" id="${c.id}">${c?.encodeAsHTML()}</g:link></li>
</g:each>
</ul>
<g:link controller="zootPage" params="['zootPage.id':zootPage?.id]" action="create">Add ZootPage</g:link>

                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="dateCreated">Date Created:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'dateCreated','errors')}">
                                    <g:datePicker name="dateCreated" value="${zootPage?.dateCreated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="filter_type">Filtertype:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'filter_type','errors')}">
                                    <input type="text" id="filter_type" name="filter_type" value="${fieldValue(bean:zootPage,field:'filter_type')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="full_path">Fullpath:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'full_path','errors')}">
                                    <input type="text" id="full_path" name="full_path" value="${fieldValue(bean:zootPage,field:'full_path')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="keywords">Keywords:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'keywords','errors')}">
                                    <input type="text" id="keywords" name="keywords" value="${fieldValue(bean:zootPage,field:'keywords')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="lastUpdated">Last Updated:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'lastUpdated','errors')}">
                                    <g:datePicker name="lastUpdated" value="${zootPage?.lastUpdated}" ></g:datePicker>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="parent">Parent:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'parent','errors')}">
                                    <g:select optionKey="id" from="${ZootPage.list()}" name="parent.id" value="${zootPage?.parent?.id}" ></g:select>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="slug">Slug:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'slug','errors')}">
                                    <input type="text" id="slug" name="slug" value="${fieldValue(bean:zootPage,field:'slug')}"/>
                                </td>
                            </tr> 
                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="title">Title:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'title','errors')}">
                                    <input type="text" id="title" name="title" value="${fieldValue(bean:zootPage,field:'title')}"/>
                                </td>
                            </tr> 
                        
                        </tbody>
                    </table> -->
                </div>
                <div class="buttons">
                    <span class="button"><g:actionSubmit class="save" value="Update" /></span>
                    <span class="button"><g:actionSubmit class="delete" onclick="return confirm('Are you sure?');" value="Delete" /></span>
                </div>
            </g:form>
        </div>
    </body>
</html>
