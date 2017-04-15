package cscie56.ps5

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(Comment)
@Mock(BlogEntry)
class CommentSpec extends Specification {

    def setup() {
    }

    def cleanup() {
    }

    void "test text constraints"() {
        when:
        def blog = Mock(BlogEntry)
        def comment = new Comment(text: "", dateCreated: new Date(), blogpost: blog)
        then:
        !comment.validate()
        when:
        comment = new Comment(text: "Comment content", dateCreated: new Date(), blogpost: blog)
        then:
        comment.validate()
    }

    void "test date constraints"() {
        when:
        def blog = Mock(BlogEntry)
        def comment = new Comment(text: "", dateCreated: new Date()+1, blogpost: blog)
        then:
        !comment.validate()
    }
}
