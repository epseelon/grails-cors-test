package com.adessa.unbox

import grails.plugin.springsecurity.annotation.Secured
import grails.rest.*
import grails.converters.*

@Secured(value=['ROLE_COMPANY_ADMIN'])
class TodoController {
	static responseFormats = ['json']

    def todoService

    @Secured(value=['ROLE_COMPANY_ADMIN'])
    def index() {
        def dtos = todoService.getTodos()
        render dtos as JSON
    }
}
