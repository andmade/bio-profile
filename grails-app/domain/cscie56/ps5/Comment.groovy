package cscie56.ps5

class Comment {

    BlogEntry blogpost
    String text
    Date dateCreated

    static constraints = {
        text(blank:false)
//        dateCreated(max:new Date())
    }
}
