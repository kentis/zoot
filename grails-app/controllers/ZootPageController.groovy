import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import groovy.text.Template
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.pages.GSPResponseWriter
import org.springframework.web.context.request.RequestContextHolder
import com.petebevin.markdown.MarkdownProcessor
import grails.converters.deep.JSON
import grails.converters.deep.XML

//import no.machina.zoot.converters.XML
import no.machina.zoot.domain.*

class ZootPageController {
		def authenticationService
    //def groovyPagesTemplateEngine
		def zootService
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST', render:'POST']
		def beforeInterceptor = [action:this.&auth,except:['show','login']]
		def afterInterceptor = { model ->
			model["root"] = ZootPage.getRoot()
			model["filters"] = zootService.getAvailableFilters()
		}
	
		def auth() {
			if(authenticationService) {
				if (!authenticationService.isLoggedIn(request)) {
             redirect(action: "login")
	   		}
			}
		}

		def login = {

		}

    def list = {
				def root = ZootPage.getRoot()
	      withFormat{
					xhtml{
						return (root ? [ zootPageList: [root] ] :   [ zootPageList: [] ])
					}
					html{
						return (root ? [ zootPageList: [root] ] :   [ zootPageList: [] ])
					}
					xml{
						render (contentType:"text/xml", text: root.toXML(), encoding:"UTF-8")
					}
				}
    }

		def reorder = {
			switch(request.method){
				case "POST":
					def subject = ZootPage.get(params.subject)
					switch(params.cmd){
						case "up":
							subject.move_up()
							break
						case "down":
							subject.move_down()
							break
					}
					redirect(controller: "zootPage", action: "reorder", id: params.id)
				break
			}
			[page: ZootPage.get(params.id)]
		}

		def imp = {
			switch(request.method){
				case "GET":
				break
				case "POST":
					def page = new ZootPage()
					def xml = new XmlParser().parseText(request.getFile('file').inputStream.text)
					ZootPage.xmlToPageTree(xml, page)
					page.parent = ZootPage.getRoot()
					if(! page.save() ) {
						page.errors.each {
							println it
						}
					}
					page.saveTheChildren()
					redirect(controller: "zootPage", action:list)
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
					render(view: "generic_page", model: [title: zootPage.title, layout: zootPage.layout, body: zootService.renderBody(zootPage.body, zootPage.title, zootPage.filter_type, zootPage), page: zootPage, root: ZootPage.getRoot()] )
				}
    }

		def render = {
			render(view: "generic_page", model: [title: params.title, layout: params.layout, body: zootService.renderBody( params.body, params.title, params.filter_type ), page: null, root: ZootPage.getRoot()] )
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
        if(!zootPage) {
            flash.message = "ZootPage not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ zootPage : zootPage ]
        }
    }

    def update = {
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
            redirect(controller: 'zootPage', action:edit,id:params.id)
        }
    }

    def create = {
        def zootPage = new ZootPage()
        zootPage.properties = params
        return ['zootPage':zootPage]
    }

    def save = {
        def zootPage = new ZootPage(params)
				zootPage.set_last()
				if(zootPage.parent) zootPage.parent.addToChildren(zootPage)
        if(!zootPage.hasErrors() && zootPage.save()) {
            flash.message = "ZootPage ${zootPage.id} created"
            redirect(controller: 'zootPage', action:list)
        }
        else {
            render(view:'create',model:[zootPage:zootPage])
        }
    }
}
