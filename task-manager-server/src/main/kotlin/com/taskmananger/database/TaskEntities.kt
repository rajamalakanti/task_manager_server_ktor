package com.taskmananger.database

import org.ktorm.entity.Entity
import org.ktorm.schema.Table
import org.ktorm.schema.int
import org.ktorm.schema.varchar
import org.ktorm.schema.boolean


object DBTaskTable: Table<DBTaskEntity>("task") {

    val id = int("task_id").primaryKey().bindTo { it.id }
    val title = varchar("description").bindTo { it.title }
    val done = boolean("done").bindTo { it.done }
}

interface DBTaskEntity: Entity<DBTaskEntity> {

    companion object : Entity.Factory<DBTaskEntity>()

    val id: Int
    val title: String
    val done: Boolean
}