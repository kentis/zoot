import no.machina.zoot.domain.*

class RevisionsController {
	def zootService
	def show = {
		def zootPageRevision = ZootPageRevision.get(params.id)
		render(view: "/zootPage/generic_page", model: [title: params.title, body: zootService.renderBody( zootPageRevision.body, zootPageRevision.title, zootPageRevision.filter_type ), page: zootPageRevision.zootPage, root: ZootPage.getRoot()])
	}
	
	def diff = {
		def zootPageRevision = ZootPageRevision.get(params.id)
		[revision: zootPageRevision]
	}

	def revert = {
		def zootPageRevision = ZootPageRevision.get(params.id)
		def zootPage = zootPageRevision.zootPage
		def newRevision = new ZootPageRevision(zootPage)
		zootPage.title = zootPageRevision.title
    zootPage.slug = zootPageRevision.slug
    zootPage.keywords = zootPageRevision.keywords
    zootPage.body = zootPageRevision.body
    zootPage.filter_type = zootPageRevision.filter_type
    zootPage.author = zootPageRevision.author
		if(!zootPage.hasErrors() && zootPage.save()) {
			newRevision.save()
			flash.message = "ZootPage ${params.id} reverted"
      redirect(controller: "zootPage", action: "list")
		}
	}
}
