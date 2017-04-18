package cscie56.ps5

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Comment)
@Mock([BlogEntry,User])
class CommentSpec extends Specification {

    BlogEntry blog
    User user

    def setup() {
        blog = Mock()
        user = Mock()
    }

    def cleanup() {
    }

    void "test text constraints"() {
        when:

        def comment = new Comment(text: "", dateCreated: new Date(), blogEntry: blog,user:user)
        then:
        !comment.validate()
    }

    void "test date constraints"() {
        when:
        def blog = Mock(BlogEntry)
        def comment = new Comment(text: "", dateCreated: new Date()+1, blogpost: blog,user:user)
        then:
        !comment.validate()
    }
}
