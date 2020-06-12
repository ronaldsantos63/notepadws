package com.ronaldsantos.notepadws.repository

import com.ronaldsantos.notepadws.model.Note
import org.springframework.data.jpa.repository.JpaRepository

interface NoteRepository : JpaRepository<Note, Long>