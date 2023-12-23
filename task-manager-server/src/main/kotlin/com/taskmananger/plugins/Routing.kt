package com.taskmananger.plugins

import com.taskmananger.entities.ToDoDraft
import com.taskmananger.repo.DBToDoRepository
import com.taskmananger.repo.ToDoRepository
import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.request.*
import io.ktor.server.response.*
import io.ktor.server.routing.*

fun Application.configureRouting() {
    routing {

        val repository: ToDoRepository = DBToDoRepository()

        get("/") {
            call.respondText("Todo List")
        }

        get("/todos") { // return all todo list items
            call.respond(repository.getAllToDos())
        }

        get("/todos/{id}") { // return specific todo list item
            val id = call.parameters["id"]?.toIntOrNull()

            if (id == null) {
                call.respond(HttpStatusCode.BadRequest, "id has to be a number!")
                return@get
            }

            val todo = repository.getToDo(id)

            if (todo == null) {
                call.respond(HttpStatusCode.NotFound, "task $id not found :(")
            } else {
                call.respond(todo)
            }
            call.respondText { "Details for Todo Item #$id" }
        }

        post("/todos") {// create todo list item
            val toDoDraft = call.receive<ToDoDraft>()
            val todo = repository.addToDo(toDoDraft)
            call.respond(todo)
        }

        put("/todos/{id}") {// edit single todo list item
            val toDoDraft = call.receive<ToDoDraft>()
            val toDoId = call.parameters["id"]?.toIntOrNull()

            if (toDoId == null) {
                call.respond(HttpStatusCode.BadRequest, "id must be a number!")
                return@put
            }

            val updated = repository.updateToDo(toDoId, toDoDraft)
            if (updated) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "found no todo id with $toDoId")
            }
        }

        delete ("/todos/{id}") { // delete single todo list item
            val toDoId = call.parameters["id"]?.toIntOrNull()

            if (toDoId == null) {
                call.respond(HttpStatusCode.BadRequest, "id must be a number!")
                return@delete
            }

            val removed = repository.removeToDo(toDoId)
            if (removed) {
                call.respond(HttpStatusCode.OK)
            } else {
                call.respond(HttpStatusCode.NotFound, "found no todo id with $toDoId")
            }
        }
    }
}
