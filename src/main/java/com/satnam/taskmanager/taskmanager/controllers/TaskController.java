package com.satnam.taskmanager.taskmanager.controllers;

import java.text.ParseException;
import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.satnam.taskmanager.taskmanager.dto.CreateTaskDTO;
import com.satnam.taskmanager.taskmanager.dto.ErrorResponseDTO;
import com.satnam.taskmanager.taskmanager.dto.NotesTaskResponseDTO;
import com.satnam.taskmanager.taskmanager.dto.UpdateTaskDTO;
import com.satnam.taskmanager.taskmanager.entities.NoteEntity;
import com.satnam.taskmanager.taskmanager.entities.TaskEntity;
import com.satnam.taskmanager.taskmanager.service.NoteService;
import com.satnam.taskmanager.taskmanager.service.TaskService;

@RestController
@RequestMapping("/tasks")
public class TaskController {
    private final TaskService taskService;
    private final NoteService noteService;
    
    public TaskController(TaskService taskService,NoteService noteService){
        this.taskService = taskService;
        this.noteService = noteService;
    }

    @GetMapping("")
    public ResponseEntity<List<TaskEntity>> getTasks(){
    return ResponseEntity.ok(taskService.getTasks());
    }

    @GetMapping("/{id}")
    public ResponseEntity<NotesTaskResponseDTO> getTaskById(@PathVariable("id") int id){
        NotesTaskResponseDTO notesTaskResponseDTO = new NotesTaskResponseDTO();
        TaskEntity task = taskService.getTaskById(id);
        if(task==null){
            return ResponseEntity.notFound().build();
        }
        List<NoteEntity> notes = noteService.getNotesForTask(id);
        notesTaskResponseDTO.setTask(task);
        notesTaskResponseDTO.setNotes(notes);
        
        return ResponseEntity.ok(notesTaskResponseDTO);
    }
    @PostMapping("")
    public ResponseEntity<TaskEntity> addTask(@RequestBody CreateTaskDTO body) throws ParseException{
        TaskEntity task =taskService.addTask(body.getTitle(), body.getDescription(), body.getDeadline());

        return ResponseEntity.ok(task);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<TaskEntity> updateTask(@PathVariable("id") int id,@RequestBody UpdateTaskDTO body) throws ParseException{
        TaskEntity task = taskService.updateTask(id, body.getDescription(), body.getDeadline(), body.getCompleted());
        if(task!=null)
        return ResponseEntity.ok(task);
        return null;
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponseDTO> handleErrors(Exception e){
        if(e instanceof ParseException){
            return ResponseEntity.badRequest().body(new ErrorResponseDTO("Invalid date format"));
        }
        return ResponseEntity.internalServerError().body(new ErrorResponseDTO("Internal Server Error"));
    }

}
