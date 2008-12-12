                <div class="dialog">
                    <table>
                        <tbody>
                        
                            <tr class="prop">
                       
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
                                    <label for="author">Author:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'author','errors')}">
                                    <input type="text" id="author" name="author" value="${fieldValue(bean:zootPage,field:'author')}"/>
                                </td>
                            </tr> 
                        
                            <g:if test="${!zootPage?.id}">                        
                            <tr class="prop">
                                <td valign="top" class="name">
                                    <label for="parent">Parent:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'parent','errors')}">
                                    <g:select optionKey="id" from="${ZootPage.list()}" name="parent.id" value="${zootPage?.parent?.id}" ></g:select>
                                </td>
                            </tr> 
                        		</g:if>
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
                        
														<tr class="prop">
                                <td valign="top" class="name">
                                    <label for="body">Body:</label>
                                </td>
                                <td valign="top" class="value ${hasErrors(bean:zootPage,field:'body','errors')}">
                                    <g:textArea id="body" name="body" rows="80" cols="60" value="${zootPage.body}" />
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

                        </tbody>
                    </table>
                </div>
