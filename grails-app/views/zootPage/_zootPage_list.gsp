<g:each in="${zootPageList}" status="i" var="zootPage">
                      <li>  
												<h2><g:link action="edit" id="${zootPage.id}">${zootPage.title}</g:link></h2>
												<p>last updated: ${zootPage.lastUpdated}  ${zootPage.children.size()}</p>
												<ul>
													<g:render template="zootPage_list" model="[zootPageList: zootPage.children]" plugin="zoot"/>
												</ul>
											</li>
</g:each>
