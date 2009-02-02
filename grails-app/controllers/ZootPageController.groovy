import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import groovy.text.Template
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.pages.GSPResponseWriter
import org.springframework.web.context.request.RequestContextHolder
import com.petebevin.markdown.MarkdownProcessor
import grails.converters.deep.JSON
import no.machina.zoot.converters.XML

class ZootPageController {
		def authenticationService
    //def groovyPagesTemplateEngine
		def zootService
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST', render:'POST']
		def beforeInterceptor = [action:this.&auth,except:'show']
		def afterInterceptor = { model ->
			model["root"] = ZootPage.getRoot()
		}
	
		def auth() {
			if(authenticationService) {
				if (!authenticationService.isLoggedIn(request)) {
             response.sendError(403)
	   		}
			}
		}

    def list = {
				def root = ZootPage.getRoot()
				def childsOfRoot = ZootPage.findAllByParent(root)
	      withFormat{
					xhtml{
						return (root ? [ zootPageList: [root] ] :   [ zootPageList: [] ])
					}
					html{
						return (root ? [ zootPageList: [root] ] :   [ zootPageList: [] ])
					}
					xml{
						render root as XML
					}
				}
    }

		def imp = {
			switch(request.method){
				case "GET":
				break
				case "POST":
					//request.content = request.getFile('file').inputStream.text.bytes
					def page = new ZootPage()
					def xml = new XmlParser().parseText(request.getFile('file').inputStream.text)
					ZootPage.xmlToPageTree(xml, page)
					//println page
					//println page.children
					//println page.validate()
					page.parent = ZootPage.getRoot()
					if(! page.save() ) {
						page.errors.each {
							println it
						}
					}
					page.saveTheChildren()
					//def xml = XmlSlurper.parseText(request.getFile('file').inputStream.text)
					//println xml
					println "save ${page.title} with ${page.children.size()} children"
					page.children.each{
						println "\t${it.id}\t${it.title}\t${it.parent.id}"
					}
					redirect(action:list)
				break
				
			}
		}

    def show = {
				def zootPage = null
        if(params.path) zootPage = ZootPage.findPageByPath(params.path)
				if(!zootPage && params.id) zootPage = ZootPage.get( params.id.toInteger() )
				if(!zootPage && !params.path && !params.id) zootPage = ZootPage.getRoot()

        if(!zootPage) {
					response.status = 404 //Not Found            
						render "Zoot page not found for path ${params.path}"
        }
        else {
					render(view: "generic_page", model: [title: zootPage.title, body: zootService.renderBody(zootPage.body, zootPage.title, zootPage.filter_type, zootPage), page: zootPage, root: ZootPage.getRoot()] )
				}
    }

		def render = {
			render(view: "generic_page", model: [title: params.title, body: zootService.renderBody( params.body, params.title, params.filter_type ), page: null, root: ZootPage.getRoot()] )
		}

		def revisions = {
			def zootPage = ZootPage.get(params.id)
			def currentRevision = zootService.renderBody(zootPage.body, zootPage.title, zootPage.filter_type, zootPage);
			def revisionPage = ZootPageRevision.get(params.revision_id)
			def revision = zootService.renderBody(revisionPage.body, revisionPage.title, revisionPage.filter_type, zootPage);
			return [title: zootPage.title, currentRevision: currentRevision, revision: revision, page: zootPage, root: ZootPage.getRoot()]
		}

    def delete = {
        def zootPage = ZootPage.get( params.id )
        if(zootPage) {
            zootPage.delete()
            flash.message = "ZootPage ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "ZootPage not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def zootPage = ZootPage.get( params.id )
				//println zootPage.revisions
        if(!zootPage) {
            flash.message = "ZootPage not found with id ${params.id}"
            redirect(action:list)
        }
        else {
						//println "body: ${zootPage.body}"
            return [ zootPage : zootPage ]
        }
    }

    def update = {
				//println "updating page: ${params}"
        def zootPage = ZootPage.get( params.id )
        if(zootPage) {
						def revision = new ZootPageRevision(zootPage)
            zootPage.properties = params
            if(!zootPage.hasErrors() && zootPage.save()) {
								revision.save()
                flash.message = "ZootPage ${params.id} updated"
                redirect(action:list)
            }
            else {
                render(view:'edit',model:[zootPage:zootPage])
            }
        }
        else {
            flash.message = "ZootPage not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def zootPage = new ZootPage()
        zootPage.properties = params
        return ['zootPage':zootPage]
    }

    def save = {
        def zootPage = new ZootPage(params)
        if(!zootPage.hasErrors() && zootPage.save()) {
            flash.message = "ZootPage ${zootPage.id} created"
            redirect(action:list)
        }
        else {
            render(view:'create',model:[zootPage:zootPage])
        }
    }
}
