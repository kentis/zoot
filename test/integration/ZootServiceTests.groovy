class ZootServiceTests extends GroovyTestCase {

	void testAvailableFilters(){
		ZootService service = new ZootService()
		service.metaClass.fckEditorExists = { -> return false }
		assertEquals(["gsp","markdown"], service.getAvailableFilters())

		service = new ZootService()
		service.metaClass.fckEditorExists = { -> return true }
		
		assertEquals(["gsp","markdown", "wysiwyg html"], service.getAvailableFilters())
	}

}
