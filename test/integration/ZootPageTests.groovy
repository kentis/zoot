import grails.converters.*
import no.machina.zoot.domain.*

class ZootPageTests extends GroovyTestCase {
	
//	void testFail(){
	//	fail "failed"
//	}

	void testFindPageByPath(){
		def p1 = new ZootPage(title:"p1", author:"meg",pos:1)
    if( !p1.save() ) {
      p1.errors.each {
        println it
      }
    }
    def p2 = new ZootPage(title:"p2", slug:"2", author:"meg", parent:p1,pos:2)
    if( !p2.save() ) {
      p2.errors.each {
        println it
      }
    }
		def p3 = new ZootPage(title:"p3", slug:"3", author:"meg", parent: p2,pos: 3)
    if( !p3.save() ) {
      p3.errors.each {
        println it
      }
    }
		
		assertNotNull ZootPage.findPageByPath("")
		println "children: ${ZootPage.findPageByPath("2").children}"
		assertEquals "p1", ZootPage.findPageByPath("").title;
		assertEquals "p2", ZootPage.findPageByPath("2").title;
		assertEquals "p3", ZootPage.findPageByPath("2/3").title;
		assertNull ZootPage.findPageByPath("1/blslblsd")
	}

	void testtoPath() {
		createSimplePageStructure()
		def p3 = ZootPage.findByTitle("p3")
		assertEquals "1/2/3", p3.toPath()
	}

	private void createSimplePageStructure() {
		def p1 = new ZootPage(title:"p1", author:"meg",slug:"1", pos:1)
    if( !p1.save() ) {
      p1.errors.each {
        println it
      }
    }
    def p2 = new ZootPage(title:"p2", slug:"2", author:"meg", parent:p1, pos: 1)
    if( !p2.save() ) {
      p2.errors.each {
        println it
      }
    }
		def p3 = new ZootPage(title:"p3", slug:"3", author:"meg", parent: p2, pos: 1)
    if( !p3.save() ) {
      p3.errors.each {
        println it
      }
    }

	}
}
