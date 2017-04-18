package cscie56.ps5

import grails.plugin.springsecurity.annotation.Secured

import static org.springframework.http.HttpStatus.*
import grails.transaction.Transactional

@Transactional(readOnly = true)
@Secured([Role.ROLE_ADMIN, Role.ROLE_USER])
class BlogEntryController {

    static allowedMethods = [save: "POST", update: "PUT", delete: "DELETE"]

    def springSecurityService

    @Secured([Role.ROLE_ADMIN, Role.ROLE_USER,Role.ROLE_ANONYMOUS])
    def index(Integer max) {
        params.max = Math.min(max ?: 10, 100)
        respond BlogEntry.list(params), model:[blogEntryCount: BlogEntry.count()]
    }

    @Secured([Role.ROLE_ADMIN, Role.ROLE_USER,Role.ROLE_ANONYMOUS])
    def show(BlogEntry blogEntry) {
        def unrejected_comments = []
        User user = springSecurityService.isLoggedIn() ? springSecurityService.loadCurrentUser() :   null
        if(user && user == blogEntry.poster) {
            unrejected_comments = Comment.findAllByBlogEntryAndRejected(blogEntry, false).sort { c1, c2 -> c2.dateCreated <=> c1.dateCreated }
        } else {
            unrejected_comments = Comment.findAllByBlogEntryAndApproved(blogEntry, true).sort { c1, c2 -> c2.dateCreated <=> c1.dateCreated }
        }
        def rejected_comments = Comment.findAllByBlogEntryAndRejected(blogEntry, true).sort { c1, c2 -> c1.dateCreated <=> c2.dateCreated }


        respond blogEntry, model:[unrejected_comments:unrejected_comments,
                                  rejected_comments: rejected_comments,
                                user: user]
    }


    def create() {
        respond new BlogEntry(params)
    }

    @Transactional
    def save(BlogEntry blogEntry) {
        if (blogEntry == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (blogEntry.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond blogEntry.errors, view:'create'
            return
        }

        blogEntry.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'blogEntry.label', default: 'BlogEntry'), blogEntry.id])
                redirect blogEntry
            }
            '*' { respond blogEntry, [status: CREATED] }
        }
    }

    @Transactional
    def _save(BlogEntry blogEntry) {
        if (blogEntry == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if(blogEntry.published) {
            blogEntry.datePublished = new Date()
        }

        if (blogEntry.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond blogEntry.errors, view:'create'
            return
        }



        blogEntry.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.created.message', args: [message(code: 'blogEntry.label', default: 'BlogEntry'), blogEntry.id])
                redirect blogEntry
            }
            '*' { respond blogEntry, [status: CREATED] }
        }
    }

    def edit(BlogEntry blogEntry) {
        respond blogEntry
    }

    @Transactional
    def update(BlogEntry blogEntry) {
        if (blogEntry == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        if (blogEntry.hasErrors()) {
            transactionStatus.setRollbackOnly()
            respond blogEntry.errors, view:'edit'
            return
        }

        blogEntry.save flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.updated.message', args: [message(code: 'blogEntry.label', default: 'BlogEntry'), blogEntry.id])
                redirect blogEntry
            }
            '*'{ respond blogEntry, [status: OK] }
        }
    }

    @Transactional
    def delete(BlogEntry blogEntry) {

        if (blogEntry == null) {
            transactionStatus.setRollbackOnly()
            notFound()
            return
        }

        blogEntry.delete flush:true

        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.deleted.message', args: [message(code: 'blogEntry.label', default: 'BlogEntry'), blogEntry.id])
                redirect action:"index", method:"GET"
            }
            '*'{ render status: NO_CONTENT }
        }
    }

    protected void notFound() {
        request.withFormat {
            form multipartForm {
                flash.message = message(code: 'default.not.found.message', args: [message(code: 'blogEntry.label', default: 'BlogEntry'), params.id])
                redirect action: "index", method: "GET"
            }
            '*'{ render status: NOT_FOUND }
        }
    }
}
