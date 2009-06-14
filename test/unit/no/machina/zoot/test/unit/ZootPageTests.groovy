package no.machina.zoot.test.unit 

import no.machina.zoot.domain.*

class ZootPageTests extends GroovyTestCase {
	
	void testtoString(){
		ZootPage page = new ZootPage(id: 1, slug: "dill")
		assertEquals("ZootPage #1 dill", page.toString())
	}
	
}
