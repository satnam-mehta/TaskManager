package com.satnam.taskmanager.taskmanager.controllers;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satnam.taskmanager.taskmanager.dto.CreateNoteDTO;
import com.satnam.taskmanager.taskmanager.entities.NoteEntity;
import com.satnam.taskmanager.taskmanager.service.NoteService;

@RestController
@RequestMapping("/tasks/{taskId}/notes")
public class NoteController {
    private NoteService noteService;

    public NoteController(NoteService noteService){
        this.noteService = noteService;
    }
    @GetMapping("")
    public ResponseEntity<List<NoteEntity>> getNotes(@PathVariable("taskId") int id){
        List<NoteEntity> l = noteService.getNotesForTask(id);
        return ResponseEntity.ok(l);
    }
    @PostMapping("")
    public ResponseEntity<NoteEntity> addNoteForTask(@PathVariable("taskId") int id,@RequestBody CreateNoteDTO body){
        NoteEntity note = noteService.addNoteForTask(id,body.getTitle(), body.getBody());

        return ResponseEntity.ok(note);
    }
}
