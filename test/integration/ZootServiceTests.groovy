class ZootServiceTests extends GroovyTestCase {

	void testAvailableFilters(){
		ZootService.metaClass.fckEditorExists = {-> false }
		assertEquals(["gsp","markdown"], new ZootService().getAvailableFilters())
		
		ZootService.metaClass.fckEditorExists = {-> true }
		assertEquals(["gsp","markdown", "wysiwyg html"], new ZootService().getAvailableFilters())
	}

}
