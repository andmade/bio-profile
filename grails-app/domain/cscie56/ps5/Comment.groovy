package cscie56.ps5

class Comment {

    BlogEntry blogEntry
    String text
    Date dateCreated
    Boolean approved = false
    Boolean rejected = false
    User poster

    static constraints = {
        text(blank:false)
    }
}