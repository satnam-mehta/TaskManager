package com.satnam.taskmanager.taskmanager.dto;

import java.util.List;

import com.satnam.taskmanager.taskmanager.entities.NoteEntity;
import com.satnam.taskmanager.taskmanager.entities.TaskEntity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class NotesTaskResponseDTO {
    private int id;
    private String title;
    private String description;
    private String deadline;
    private List<NoteEntity> notes;
    private boolean completed;
    public void setTask(TaskEntity task){
        id=task.getId();
        title=task.getTitle();
        description=task.getDescription();
        deadline=task.getDescription();
        completed=task.isCompleted();
    }
}
