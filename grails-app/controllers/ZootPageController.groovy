class ZootPageController {
    
    def index = { redirect(action:list,params:params) }

    // the delete, save and update actions only accept POST requests
    def allowedMethods = [delete:'POST', save:'POST', update:'POST']

    def list = {
				def root = ZootPage.getRoot()
        return (root ? [ zootPageList: [root] ] :   [ zootPageList: [] ])
    }

    def show = {
				println "showing zootPage $params.path"
				def zootPage = null
        if(params.path) zootPage = ZootPage.findPageByPath(params.path)
				else if(params.id) zootPage = ZootPage.get( params.id )
				else zootPage = ZootPage.getRoot()

        if(!zootPage) {
					response.status = 404 //Not Found            
						render "Zoot page not found for path ${params.path}"
        }
        else {  
					render zootPage
				}
    }

    def delete = {
        def zootPage = ZootPage.get( params.id )
        if(zootPage) {
            zootPage.delete()
            flash.message = "ZootPage ${params.id} deleted"
            redirect(action:list)
        }
        else {
            flash.message = "ZootPage not found with id ${params.id}"
            redirect(action:list)
        }
    }

    def edit = {
        def zootPage = ZootPage.get( params.id )

        if(!zootPage) {
            flash.message = "ZootPage not found with id ${params.id}"
            redirect(action:list)
        }
        else {
            return [ zootPage : zootPage ]
        }
    }

    def update = {
        def zootPage = ZootPage.get( params.id )
        if(zootPage) {
            zootPage.properties = params
            if(!zootPage.hasErrors() && zootPage.save()) {
                flash.message = "ZootPage ${params.id} updated"
                redirect(action:show,id:zootPage.id)
            }
            else {
                render(view:'edit',model:[zootPage:zootPage])
            }
        }
        else {
            flash.message = "ZootPage not found with id ${params.id}"
            redirect(action:edit,id:params.id)
        }
    }

    def create = {
        def zootPage = new ZootPage()
        zootPage.properties = params
        return ['zootPage':zootPage]
    }

    def save = {
        def zootPage = new ZootPage(params)
        if(!zootPage.hasErrors() && zootPage.save()) {
            flash.message = "ZootPage ${zootPage.id} created"
            redirect(action:list)
        }
        else {
            render(view:'create',model:[zootPage:zootPage])
        }
    }
}
