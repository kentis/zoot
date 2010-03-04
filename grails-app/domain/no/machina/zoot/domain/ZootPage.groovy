package no.machina.zoot.domain
import groovy.xml.MarkupBuilder

class ZootPage {
 static final filters = ["gsp","markdown","wysiwyg html"]
 //String full_path --nyi
 String title
 String slug
 String keywords
 String ingres
 String body
 String filter_type = "gsp"
 String author
 String layout
 Long pos
 // Long current_version  -- versions not yet supported.
 Date dateCreated
 Date lastUpdated
 List children

 Map<String, String> fields

 /** hierarcical */
 ZootPage parent 
 static hasMany = [ revisions: ZootPageRevision, children: ZootPage] 
 //static transients = [ "children" ]

 //static fetchMode = [children:"eager"]
 static constraints = { 
							parent(nullable: true) 
							slug(nullable:true)
							keywords(nullable: true)
							ingres(nullable:true)
							body(nullable:true)
							filter_type(inList: filters)
							layout(nullable:true)
					} 

	//allow long bodies
 	static mapping = {
  	body type: 'text'
	}


	/** utlitiy mehtods TODO: move some of these to sevice classes */

	def set_last(){
		def last_position
		if(parent){
			last_position = ZootPage.executeQuery("select max(pos) from ZootPage p where parent = :parent", [parent: parent])
		}else{
			last_position = ZootPage.executeQuery("select max(pos) from ZootPage p where parent is null")
		}
		if(!last_position || ! (last_position instanceof Number) ) last_position = 0
		this.pos = last_position + 1
	}
	

	/**
	*Changes the position of this page with the page above
	*/
	def move_up(){
		def over = ZootPage.findByPosAndParent(this.pos - 1, this.parent)
		if(over){
			over.pos = this.pos
			this.pos = this.pos - 1
			over.save()
			this.save()
		}
		
	}

	/**
	*Changes the position of this page with the page below
	*/
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
					case 'java.util.Map':
						def map = [:]
						it.each { child ->
							map."${child.name()}" = child.text()
						}
						this."${it.name()}" = map
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

	String toXML() {
		def writer = new StringWriter()
		def xml = new MarkupBuilder(writer)
		_toXML(xml, this)
		return "<?xml version=\"1.0\" encoding=\"UTF-8\"?>\n${writer.toString()}"
	}
	
	void _toXML(xml, page){
		xml.zootPage(id: page.id){
			xml.author(page.author)
			xml.body(page.body)
			xml.dateCreated(page.dateCreated.toString())
			xml.filter_type(page.filter_type)
			if(page.ingres) xml.ingres(page.ingres)
			if(page.keywords) xml.keywords(page.keywords)
			xml.lastUpdated(page.lastUpdated.toString())
			xml.pos(page.pos.toString())
			if(page.slug) xml.slug(page.slug)
			if(page.title) xml.title(page.title)
			if(page.layout) xml.layout(page.layout)
			if(page.fields) addFields(xml, page.fields)
			xml.children(){
				page.children.each{ child ->
					if(child != null) _toXML(xml, child) 
				}
			}
		}
	}
	
	void addFields(xml, fields){
		xml.fields{
			fields.each{ key, value ->
				xml."${key}"(value)
			}
		}	
	}

}
