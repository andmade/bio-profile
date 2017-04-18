package cscie56.ps5

class BlogEntry {

    String text
    String title
    Date dateCreated
    Date datePublished = null
    Boolean published = false
    User poster

    static transients = ['comments']

    def beforeInsert() {
        if(this.published && this.datePublished == null) {this.datePublished = new Date()}
    }

    def beforeUpdate() {
        beforeInsert()
    }

    static constraints = {
        text(blank:false)
        title(blank:false)
        datePublished(nullable: true, validator: {val,obj,errors->
            if (val > new Date()) {
                errors.rejectValue('datePublished',"Error: datePublished cannot be in the future")
            }
        })
    }

    List<Comment> getComments() {
        Comment.findAllByBlogEntry(this)
    }
}
