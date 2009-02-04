class ZootPageControllerTests extends GroovyTestCase {

	void testList(){
		def p = new ZootPage(title:"fdsf", slug:"fdsf", author:"meg",pos: 1)
		if( !p.save() ) {
   		p.errors.each {
        println it
   		}
		}
		def p2 = new ZootPage(title:"fdsf", slug:"fdsf", author:"meg", parent:p, pos:1)
		if( !p2.save() ) {
      p2.errors.each {
        println it
   		}
		}
		def controller = new ZootPageController()
		def model = controller.list()
		println model
		assertEquals(1, model.zootPageList.size)

	}

	void testReorderDown(){

		createSimplePageStructure();
		def p2 = ZootPage.findByTitle("p2")
		def p3 = ZootPage.findByTitle("p3")

		assertEquals 1, p2.pos
		assertEquals 2, p3.pos

		ZootPageController.metaClass.getParams = {-> [id:p2.parent.id, subject: p2.id, cmd: "down"] }
		def controller = new ZootPageController()
		controller.request.method = "POST"
	

		def res = controller.reorder()
		p2 = ZootPage.findByTitle("p2")
		p3 = ZootPage.findByTitle("p3")
		assertEquals 2, p2.pos
		assertEquals 1, p3.pos
	}

	void testReorderUp() {
		createSimplePageStructure();
		def p2 = ZootPage.findByTitle("p2")
		def p3 = ZootPage.findByTitle("p3")

		assertEquals 1, p2.pos
		assertEquals 2, p3.pos

		ZootPageController.metaClass.getParams = {-> [id:p3.parent.id, subject: p3.id, cmd: "up"] }
		def controller = new ZootPageController()
		controller.request.method = "POST"

		def res = controller.reorder()
		assertEquals 2, ZootPage.findByTitle("p2").pos
    assertEquals 1, ZootPage.findByTitle("p3").pos
	}
		
	
	void testImp() {
		def xmlString = """<?xml version="1.0" encoding="UTF-8"?>
			<zootPage id=\"1\">
		  <author>kent</author>
		  <body>this page is intentionally left blank</body>
		  <dateCreated>2009-02-03 19:15:25.06</dateCreated>
		  <filter__type>gsp</filter__type>
		  <ingres/>
		  <keywords>root</keywords>
		  <lastUpdated>2009-02-03 19:15:25.06</lastUpdated>
		  <parent>
			  <null/>
		  </parent>
		  <pos>1</pos>
		  <revisions/>
		  <slug>root</slug>
		  <title>no content</title>
		  <children/>
			</zootPage>"""
		def request = new org.springframework.mock.web.MockMultipartHttpServletRequest();
		def file = new org.springframework.mock.web.MockMultipartFile("file", xmlString.bytes);
		request.addFile file
		request.method = "POST"
		ZootPageController.metaClass.getRequest = {-> request }
		def controller = new ZootPageController()
		assertNull ZootPage.getRoot()
		controller.imp()
		assertNotNull ZootPage.getRoot()
	}








	private void createSimplePageStructure() {
    def p1 = new ZootPage(title:"p1", author:"meg", pos: 1)
    if( !p1.save() ) {
      p1.errors.each {
        println it
      }
    }
    def p2 = new ZootPage(title:"p2", slug:"2", author:"meg", parent:p1, pos:1)
    if( !p2.save() ) {
      p2.errors.each {
        println it
      }
    }
    def p3 = new ZootPage(title:"p3", slug:"3", author:"meg", parent: p1, pos:2)
    if( !p3.save() ) {
      p3.errors.each {
        println it
      }
    }

  }

}
