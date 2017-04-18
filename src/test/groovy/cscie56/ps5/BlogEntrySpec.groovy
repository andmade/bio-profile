package cscie56.ps5

import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import spock.lang.Specification

/**
 * See the API for {@link grails.test.mixin.domain.DomainClassUnitTestMixin} for usage instructions
 */
@TestFor(BlogEntry)
@Mock(User)
class BlogEntrySpec extends Specification {

    User testuser

    def setup() {
        testuser = Mock()
    }

    def cleanup() {
    }

    void "test text constraints"() {
        when:
        def bg1 = new BlogEntry(text: "", title:"Title here", poster: testuser, dateCreated: new Date()-1, datePublished: new Date(), published:false)
        then:
        !bg1.validate()
    }

    void "test title constraints"() {
        when:
        def bg1 = new BlogEntry(text: "Blog content", title:"", poster: testuser, dateCreated: new Date()-1, datePublished: new Date(), published:false)
        then:
        !bg1.validate()
    }

    void "test datePublished constraints"() {
        when:
        def bg1 = new BlogEntry(text: "Blog content", poster: testuser, dateCreated: new Date()-1, datePublished: new Date()+1, published:false)
        then:
        !bg1.validate()
    }

    void "test BlogEntry creation"() {
        when:
        def bg1 = new BlogEntry(text: "Blog Content", title:"Title here", poster: testuser, dateCreated: new Date()-1, datePublished: new Date(), published:false)
        then:
        bg1.validate()
    }
}
