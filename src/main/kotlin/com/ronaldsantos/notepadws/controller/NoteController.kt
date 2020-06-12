package com.ronaldsantos.notepadws.controller

import com.ronaldsantos.notepadws.exceptions.NoteNotFoundException
import com.ronaldsantos.notepadws.model.Note
import com.ronaldsantos.notepadws.repository.NoteRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.hateoas.CollectionModel
import org.springframework.hateoas.EntityModel
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo
import org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn
import org.springframework.hateoas.server.mvc.linkTo
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.util.stream.Collectors

@RestController
@RequestMapping("notes")
class NoteController {

    @Autowired
    lateinit var noteRepository: NoteRepository

    @GetMapping
    fun all(): CollectionModel<EntityModel<Note>> {
        val notes = noteRepository.findAll().stream()
                .map {
                    EntityModel.of(
                            it,
                            linkTo(methodOn(NoteController::class.java).one(it.id)).withSelfRel(),
                            linkTo(methodOn(NoteController::class.java).all()).withRel("notes")
                    )

                }.collect(Collectors.toList())
        return CollectionModel.of(
                notes,
                linkTo(methodOn(NoteController::class.java).all()).withSelfRel()
        )

    }

    @PostMapping
    fun newNote(@RequestBody note: Note): Note{
        return noteRepository.save(note)
    }

    @GetMapping("/{id}")
    fun one(@PathVariable id: Long): EntityModel<Note> {
        val note = noteRepository.findById(id)
                .orElseThrow { NoteNotFoundException(id) }
        return EntityModel.of(note,
                linkTo(methodOn(NoteController::class.java).one(id)).withSelfRel(),
                linkTo(methodOn(NoteController::class.java).all()).withRel("notes")
        );
    }

    @PostMapping("/{id}")
    fun replaceNote(@RequestBody newNote: Note, @PathVariable id: Long): Note {
        return noteRepository.findById(id)
                .map {
                    it.title = newNote.title
                    it.description = newNote.description
                    return@map noteRepository.save(it)

                }.orElseGet {
                    newNote.id = id
                    return@orElseGet noteRepository.save(newNote)
                }
    }

    @DeleteMapping("/notes/{id}")
    fun deleteNote(@PathVariable id: Long){
        noteRepository.deleteById(id)
    }

}