package com.ronaldsantos.notepadws.model

import javax.persistence.Entity
import javax.persistence.GeneratedValue
import javax.persistence.Id

@Entity
class Note(
        @Id
        @GeneratedValue
        var id: Long = 0L,
        var title: String = "",
        var description: String = "")