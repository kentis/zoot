import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import org.codehaus.groovy.grails.plugins.GrailsPluginUtils
import groovy.text.Template
import com.petebevin.markdown.MarkdownProcessor
import no.machina.zoot.domain.*

class ZootService {
		def grailsApplication
		def groovyPagesTemplateEngine
    boolean transactional = true

		String renderBody(String body, String title, String type, ZootPage zootPage = null){
      switch(type) {
        case "gsp":
          Template templ = groovyPagesTemplateEngine.createTemplate(body, title);
          StringWriter writer = new StringWriter();
          templ.make([page: zootPage, root: ZootPage.getRoot()]).writeTo(writer);
          return writer.toString()
          break
				case "wysiwyg html":
        case "html":
            return  body
        case "markdown":
          return new MarkdownProcessor().markdown(body)
          break
        }
        return null
    }
          
	boolean fckEditorExists() {
		return true
	/*	if(GrailsPluginUtils.getMetadataForPlugin("fckeditor") != null) return true
		return grailsApplication.getArtefact("Controller", "FckeditorController") != null*/
	}

	def getAditionalFields() {
		grailsApplication.config.no.zoot.fields
	}

	def setAditionalFields(params, page) {
		if(page.fields == null) page.fields = [:]
		params.each{ key, value ->
			if(key.startsWith("field_")){
				page.fields."${key - "field_"}" = value
			}
		}
	}

	def getAvailableFilters() {
		println("fckEditorExists: ${this.fckEditorExists()}")
		def filters = ["gsp","markdown"]
		if(fckEditorExists()){
			filters << "wysiwyg html"
		}
		return filters
	}

	def generateSlug(parent){
		if(grailsApplication.config.no.zoot.slugGenerator){
				return grailsApplication.config.no.zoot.slugGenerator(parent)
		}
		return UUID.randomUUID().toString()
	}
}
