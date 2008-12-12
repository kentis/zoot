class ZootPage {
 /** fields and constraints are shamelessly stolen from comatose **/
 //String full_path --nyi
 String title
 String slug
 String keywords
 String body
 String filter_type = "gsp"
 String author
 // Long _position = 0 --nyi
 // Long current_version  -- versions not yet supported.
 Date dateCreated
 Date lastUpdated
 


 /** hierarcical */
 ZootPage parent 
 static hasMany = [children: ZootPage] 


 static constraints = { 
							parent(nullable: true) 
							slug(nullable:true)
							keywords(nullable: true)
							body(nullable:true)
							} 

	/*** utlitiy mehtods **/
 	static ZootPage findPageByPath(path, parentPage = ZootPage.getRoot() ){
		if(parentPage == null) return null
		println("looking for  ${path} under ${parentPage.title}")
		if(path == "" ) return parentPage
		if(path.contains("/")){
			def slug = path.substring(0, path.indexOf("/"))
			return findPageByPath( path.substring(path.indexOf("/")+1) ,ZootPage.findByParentAndSlug(parentPage, slug))
		}else{
			return ZootPage.findByParentAndSlug(parentPage, path)
		}
	}

	static ZootPage getRoot() {
		return ZootPage.findByParentIsNull();
	}
}
