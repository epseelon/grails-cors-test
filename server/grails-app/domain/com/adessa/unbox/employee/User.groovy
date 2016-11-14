package com.adessa.unbox.employee

import groovy.transform.EqualsAndHashCode
import org.apache.commons.lang3.StringUtils

@EqualsAndHashCode(includes='username')
class User implements Serializable {
    String id

	private static final long serialVersionUID = 1

	transient springSecurityService

    // S2 fields
	String username
	String password
	boolean enabled = true
	boolean accountExpired
	boolean accountLocked
	boolean passwordExpired

    // Unbox fields
    String firstName
    String lastName
    String staffNumber

	User(String username, String password) {
		this()
		this.username = username
		this.password = password
	}

	Set<Role> getAuthorities() {
		UserRole.findAllByUser(this)*.role
	}

	def beforeInsert() {
		encodePassword()
	}

	def beforeUpdate() {
		if (isDirty('password')) {
			encodePassword()
		}
	}

	protected void encodePassword() {
		password = springSecurityService?.passwordEncoder ? springSecurityService.encodePassword(password) : password
	}

	static transients = ['springSecurityService', 'balance']

	static constraints = {
		username blank: false, unique: true
		password blank: false
		staffNumber nullable: true
	}

	static mapping = {
		password column: '`password`'
	}

	int getBalance() {
  		def fundingUnits = fundings.isEmpty() ? 0 : fundings*.units.sum()
  		def spendingUnits = spendings.isEmpty() ? 0 : spendings*.units.sum()
  		fundingUnits - spendingUnits
	}

	@Override
	String toString() {
		"#$id $username"
	}
}
