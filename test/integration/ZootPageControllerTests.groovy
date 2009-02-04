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
