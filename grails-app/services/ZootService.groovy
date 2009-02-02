import org.codehaus.groovy.grails.web.pages.GroovyPagesTemplateEngine
import groovy.text.Template
import com.petebevin.markdown.MarkdownProcessor

class ZootService {
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
        case "html":
            return  body
        case "markdown":
          return new MarkdownProcessor().markdown(body)
          break
        }
        return null
    }

}
