package unbox

import com.adessa.unbox.Todo
import com.adessa.unbox.employee.Role
import com.adessa.unbox.employee.User
import com.adessa.unbox.employee.UserRole

class BootStrap {
    def SAVE_PROPS = [failOnError: true, flush: true]

    def init = { servletContext ->
        new Todo(title: "Task 1", done: false).save()
        new Todo(title: "Task 2", done: false).save()
        new Todo(title: "Task 3", done: true).save()

        createRoles()
        createSuperAdmin()
    }
    def destroy = {
    }

    def createRoles = {
        log.debug 'Bootstrap: creating roles'
        ['ROLE_COMPANY_ADMIN', 'ROLE_EMPLOYEE'].each {
            new Role(it).save(SAVE_PROPS)
        }
    }


    def createSuperAdmin = {
        def admin = new User(
                username: 'magic@unbox.be', password: 'unbox',
                firstName: 'Géraldine', lastName: 'Prévinaire',
                staffNumber: null).save(SAVE_PROPS)

        UserRole.create(admin, Role.findByAuthority('ROLE_COMPANY_ADMIN'), true)
    }
}
