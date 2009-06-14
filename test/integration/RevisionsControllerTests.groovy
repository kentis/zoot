import no.machina.zoot.domain.*

class RevisionsControllerTests extends GroovyTestCase {
		
		
		
    void testrevert() {
			def p1 = new ZootPage(title:"p1", author:"meg", body: "new content", filter_type: "gsp", pos:1)
			p1.save()
			def r1 	= new ZootPageRevision(title: "p1", author:"meg", body: "old content", filter_type: "gsp", revisionCreated: new Date(), zootPage: p1)
			if( !r1.save() ) {
	      r1.errors.each {
  	      println it
    	  }
	    }			
			
			RevisionsController.metaClass.getParams = {-> [id:r1.id] }
			def controller = new RevisionsController()
			def res = controller.revert()
			
			assertEquals "old content", ZootPage.findByTitle("p1").body
    }
		
		
		
}
