package com.adessa.unbox

import grails.transaction.Transactional

@Transactional
class TodoService {

    List<TodoListItem> getTodos() {
        return Todo.findAll().collect {
            new TodoListItem(title: it.title, done: it.done)
        }
    }
}
