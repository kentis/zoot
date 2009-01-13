import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import groovy.text.Template
import org.codehaus.groovy.grails.web.servlet.mvc.GrailsWebRequest
import org.codehaus.groovy.grails.web.pages.GSPResponseWriter
import org.springframework.web.context.request.RequestContextHolder
import com.petebevin.markdown.MarkdownProcessor
import grails.converters.deep.*

class ZootPageController {
    def groovyPagesTemplateEngine
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

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
					println page
					println page.children
					println page.validate()
					page.parent = ZootPage.getRoot()
					if(! page.save() ) {
						page.errors.each {
							println it
						}
					}
					page.saveTheChildren()
					//def xml = XmlSlurper.parseText(request.getFile('file').inputStream.text)
					//println xml
					redirect(action:list)
				break
				
			}
		}

		def xml_to_page_tree(xml, page){

		}

    def show = {
				println "showing zootPage $params.path"
				def zootPage = null
        if(params.path) zootPage = ZootPage.findPageByPath(params.path)
				if(!zootPage && params.id) zootPage = ZootPage.get( params.id )
				if(!zootPage && !params.path && !params.id) zootPage = ZootPage.getRoot()

        if(!zootPage) {
					response.status = 404 //Not Found            
						render "Zoot page not found for path ${params.path}"
        }
        else {
					switch(zootPage.filter_type) {
						case "gsp":
							Template templ = groovyPagesTemplateEngine.createTemplate(zootPage.body, zootPage.title)
							Writer out = GSPResponseWriter.getInstance(response, 8024);
			        GrailsWebRequest webRequest =  (GrailsWebRequest) RequestContextHolder.currentRequestAttributes();
  			      webRequest.setOut(out);
							templ.make([page: zootPage]).writeTo(out)
							out.close()
							render ""
							break
					case "markdown":
						render new MarkdownProcessor().markdown(zootPage.body)
						break
					}
				}
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
						println "body: ${zootPage.body}"
            return [ zootPage : zootPage ]
        }
    }

    def update = {
				println "updating page: ${params}"
        def zootPage = ZootPage.get( params.id )
        if(zootPage) {
            zootPage.properties = params
            if(!zootPage.hasErrors() && zootPage.save()) {
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
