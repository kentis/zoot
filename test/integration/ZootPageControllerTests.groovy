class ZootPageControllerTests extends GroovyTestCase {

	void testList(){
		def p = new ZootPage(title:"fdsf", slug:"fdsf", author:"meg")
		if( !p.save() ) {
   		p.errors.each {
        println it
   		}
		}
		def p2 = new ZootPage(title:"fdsf", slug:"fdsf", author:"meg", parent:p)
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

}
