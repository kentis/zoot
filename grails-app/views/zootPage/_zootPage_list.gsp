<g:each in="${zootPageList}" status="i" var="zootPage">
                      <li>  
												<h2><g:link action="edit" id="${zootPage.id}">${zootPage.title}</g:link> <g:if test="${zootPage.children?.size() > 1}"><g:link action="reorder" id="${zootPage.id}">reorder children</g:link></g:if></h2>
												<p>last updated: ${zootPage.lastUpdated}</p>
												<ul>
													<g:render template="zootPage_list" model="[zootPageList: zootPage.children]" plugin="zoot"/>
												</ul>
											</li>
</g:each>
