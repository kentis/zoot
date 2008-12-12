class UrlMappings {
    static mappings = {
      "/$controller/$action?/$id?"{
	      constraints {
			 // apply constraints here
		  }
	  }
	  "500"(view:'/error')

		"/zoot/$path**"(controller: "zootPage", action: "show", plugin: "zoot")
		
	}
}
