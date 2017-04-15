package cscie56.ps5

class BlogEntry {

    String text
    Date dateCreated
    Date datePublished
    Boolean published
    User poster

    static transients = ['comments']

    static constraints = {
        text(blank:false)
        dateCreated(max: new Date())
        datePublished(validator: {val,obj,errors->
            if (val < obj.dateCreated) {
                errors.rejectValue('datePublished',"Error: datePublished cannot be after dateCreated")
            }
            if (val > new Date()) {
                errors.rejectValue('datePublished',"Error: datePublished cannot be in the future")
            }
        })
    }

    List<Comment> getComments() {
        Comment.findAllByBlogEntry(this)
    }
}
