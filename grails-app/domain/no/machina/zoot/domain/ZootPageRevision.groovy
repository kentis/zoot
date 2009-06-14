package no.machina.zoot.domain

class ZootPageRevision {
 
 String title
 String slug
 String keywords
 String ingres
 String body
 String filter_type 
 String author
 Date dateCreated
 Date lastUpdated
 Date revisionCreated

 static belongsTo = [zootPage:ZootPage]

 
  //static fetchMode = [children:"eager"]
  static constraints = { 
		slug(nullable:true)
		keywords(nullable: true)
		body(nullable:true)
		ingres(nullable:true)
		filter_type(inList: ZootPage.filters)
	} 
	
	public ZootPageRevision(){
		
	}

	public ZootPageRevision(ZootPage orig) {
		this.title = orig.title
		this.slug = orig.slug
		this.keywords = orig.keywords
		this.body = orig.body
		this.filter_type = orig.filter_type
		this.author = orig.author
		this.revisionCreated = orig.lastUpdated
		this.zootPage = orig
	}

	String toString() {
		"Revision from ${this.revisionCreated}"
	}
}
