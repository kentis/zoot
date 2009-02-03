class ZootPage {
 static final filters = ["gsp","markdown"]
 //String full_path --nyi
 String title
 String slug
 String keywords
 String ingres
 String body
 String filter_type = "gsp"
 String author
 Long pos
 // Long current_version  -- versions not yet supported.
 Date dateCreated
 Date lastUpdated
 List children


 /** hierarcical */
 ZootPage parent 
 static hasMany = [ revisions: ZootPageRevision] 
 static transients = [ "children" ]

 static fetchMode = [children:"eager"]
 static constraints = { 
							parent(nullable: true) 
							slug(nullable:true)
							keywords(nullable: true)
							ingres(nullable:true)
							body(nullable:true)
							filter_type(inList: ZootPage.filters)
					} 


	/**
	* terrible hack to resolve problems that seem to be a bug in the handleing og hasMany.
	* This will hopefully not be nessesary anymore in grail 1.1
	*/
	def onLoad = {
		this.children = ZootPage.findAllByParent(this,[sort: "pos", order: "asc"])
	}


	/** utlitiy mehtods */

	def set_last(){
		def last_position = ZootPage.executeQuery("select max(pos) from ZootPage p where parent = :parent", [parent: parent])
		this.pos = last_pos + 1
		this.save()
	}
	
	def move_up(){
		def over = ZootPage.findByPosAndParent(this.pos - 1, this.parent)
		if(over){
			over.pos = this.pos
			this.pos = this.pos - 1
			over.save()
			this.save()
		}
		
	}
		
	def move_down(){
		def under = ZootPage.findByPosAndParent(this.pos + 1, this.parent)
		if(under){
			under.pos = this.pos
			this.pos = this.pos + 1
			under.save()
			this.save()
		}
		
	}

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

	public String toString() {
		"ZootPage #$id ${toPath()}"
	}
	
	String toPath() {
		if(parent) return "${parent.toPath()}/${slug}"
		return "$slug"
	}

	static ZootPage xmlToPageTree(xml, page){
		page.updateAttributes(xml)
	}

	void saveTheChildren() {
		println "${this.title} has ${this.children?.size()} children"
		this.children.each { 
			it.parent = this
			if(! it.save() ) {
				println "error saving ${it.title}"
        it.errors.each { err ->
         	println err
      	}
      }
			it.saveTheChildren()
			//this.addToChildren(it)
		}
	}

	void updateAttributes(xml){
		xml.each {
			
			if(it.name() != "parent" && this.metaClass.hasProperty(this, it.name())) {
				this.metaClass.getMetaProperty(it.name()).type
				println "${it.name()}: ${this.metaClass.getMetaProperty(it.name()).type.getName()}"
				switch(this.metaClass.getMetaProperty(it.name()).type.getName()){
					case 'java.lang.String':
						this."${it.name()}" = it.text()
						break
					case 'java.util.Set':
					case 'java.util.List':
						switch(it.name()) {
							case 'children':
								def set = [] as List
								it.each { child -> 
									def page = new ZootPage()
								  ZootPage.xmlToPageTree(child, page)
                  //this.addToChildren(page)
									set << page
								}
								this."${it.name()}" = set
								break
							case 'revisions':
									//ignore for now
							break
						}
						break
					case 'java.util.Date':
						this."${it.name()}" = new java.text.SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS").parse(it.text())
						break
					case 'java.lang.Long':
						this."${it.name()}" = Long.parseLong(it.text())
						break
					default:
						throw new RuntimeException("Dont know how to update an attribute of type :${this.metaClass.getMetaProperty(it.name()).type.getName()}")
				}
			}	
		}
	}
}
