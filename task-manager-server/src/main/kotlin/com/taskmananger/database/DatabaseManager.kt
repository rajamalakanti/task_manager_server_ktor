package com.taskmananger.database

import com.taskmananger.entities.ToDo
import com.taskmananger.entities.ToDoDraft
import org.ktorm.database.Database
import org.ktorm.dsl.*
import org.ktorm.entity.firstOrNull
import org.ktorm.entity.sequenceOf
import org.ktorm.entity.toList

class DatabaseManager {

    //config
    private val hostname = "Rajs-MacBook-Pro-3.local"
    private val databaseName = "TaskTrackerDB"
    private val username = "root"
    private val password = "password"

    //database
    private val ktormDatabase: Database

    init {
        val jdbcUrl = "jdbc:mysql://$hostname:3306/$databaseName?user=$username&password=$password&useSSL=false&allowPublicKeyRetrieval=true"
        ktormDatabase = Database.connect(jdbcUrl)
    }

    fun getAllTodos(): List<DBTaskEntity> {
        return ktormDatabase.sequenceOf(DBTaskTable).toList()
    }

    fun getTodo(id: Int): DBTaskEntity? {
        return ktormDatabase.sequenceOf(DBTaskTable)
            .firstOrNull { it.id eq id }
    }

    fun addTodo(draft: ToDoDraft): ToDo {

        val insertedId = ktormDatabase.insertAndGenerateKey(DBTaskTable) {
            set(DBTaskTable.title, draft.title)
            set(DBTaskTable.done, draft.done)
        } as Int

        return ToDo(insertedId, draft.title, draft.done)
    }

    fun updateTodo(id: Int, draft: ToDoDraft): Boolean {
        val updatedRows = ktormDatabase.update(DBTaskTable) {
            set(DBTaskTable.title, draft.title)
            set(DBTaskTable.done, draft.done)
            where { it.id eq id }

        }

        return updatedRows > 0
    }

    fun removeTodo(id: Int): Boolean {
        val deletedRows = ktormDatabase.delete(DBTaskTable) {
            it.id eq id
        }
        return deletedRows > 0
    }

}
