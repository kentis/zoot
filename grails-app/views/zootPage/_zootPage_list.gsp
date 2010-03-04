<%! import no.machina.zoot.domain.* %>
<g:each in="${zootPageList}" status="i" var="zootPage">
                      <li "${open ? '' : 'class=\"closed\"'}">  
												<span><g:link class="editLink" action="edit" id="${zootPage.id}">${zootPage.title}</g:link> <g:link class="createSubPage" action="create" id="${zootPage.id}">new page</g:link>   <g:if test="${zootPage.children?.size() > 1}"><g:link class="reorderLink" action="reorder" id="${zootPage.id}">reorder children</g:link></g:if></span>
												<p>last updated: ${zootPage.lastUpdated}</p>
												<g:if test="${zootPage.children.size() > 0}">
													<ul class="sub-page-list">
														<g:render template="zootPage_list" model="[zootPageList: ZootPage.findAllByParent(zootPage, [order:'asc', sort: 'pos']), open: false]" plugin="zoot"/>
													</ul>
												</g:if>
											</li>
</g:each>
