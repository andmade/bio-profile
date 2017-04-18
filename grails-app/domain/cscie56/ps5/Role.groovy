package cscie56.ps5

import groovy.transform.EqualsAndHashCode
import groovy.transform.ToString

@EqualsAndHashCode(includes='authority')
@ToString(includes='authority', includeNames=true, includePackage=false)
class Role implements Serializable {

	private static final long serialVersionUID = 1
	public static final String ROLE_USER = "ROLE_USER"
	public static final String ROLE_ADMIN = "ROLE_ADMIN"
	public static final String ROLE_ANONYMOUS = "ROLE_ANONYMOUS"



	String authority

	static constraints = {
		authority blank: false, unique: true
	}

	static mapping = {
		cache true
	}
}
