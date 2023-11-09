package com.satnam.taskmanager.taskmanager.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.springframework.stereotype.Service;

import com.satnam.taskmanager.taskmanager.entities.NoteEntity;
import com.satnam.taskmanager.taskmanager.entities.TaskEntity;

@Service
public class NoteService {
    private TaskService taskService;
    private int noteId = 1;
    private HashMap<Integer,List<NoteEntity>> taskNotes = new HashMap<>();

    public NoteService(TaskService taskService){
        this.taskService = taskService;
    }
    public List<NoteEntity> getNotesForTask(int id){
        TaskEntity task = taskService.getTaskById(id);
        if(task == null)
        return null;
        return taskNotes.get(id);
    }

    public NoteEntity addNoteForTask(int taskId,String title, String body){
        TaskEntity task = taskService.getTaskById(taskId);
        if(task == null)
        return null;

        NoteEntity note = new NoteEntity();

        note.setId(noteId);
        noteId++;
        note.setTitle(title);
        note.setBody(body);

        if(taskNotes.get(taskId)==null){
            taskNotes.put(taskId, new ArrayList<>());
        }
        taskNotes.get(taskId).add(note);

        return note;
    }
}
