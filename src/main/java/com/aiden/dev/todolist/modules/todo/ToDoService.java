package com.aiden.dev.todolist.modules.todo;

import com.aiden.dev.todolist.modules.todo.form.AddToDoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
public class ToDoService {

    private final ToDoRepository toDoRepository;

    public void addToDo(AddToDoForm addToDoForm) {
        ToDo todo = ToDo.builder()
                .title(addToDoForm.getTitle())
                .contents(addToDoForm.getContents())
                .status(ToDoStatus.TODO)
                .build();
        toDoRepository.save(todo);
    }

    public List<ToDo> getToDos(ToDoStatus status) {
        return toDoRepository.findByStatus(status);
    }

    public void updateToDoLeft(Long toDoId) {
        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow(() -> new IllegalArgumentException(toDoId + "에 해당하는 ToDo가 존재하지 않습니다."));
        ToDoStatus leftStatus = toDo.getStatus();
        if(leftStatus == ToDoStatus.WORKING) {
            leftStatus = ToDoStatus.TODO;
        } else if(leftStatus == ToDoStatus.DONE) {
            leftStatus = ToDoStatus.WORKING;
        }
        toDo.setStatus(leftStatus);
    }

    public void updateToDoRight(Long toDoId) {
        ToDo toDo = toDoRepository.findById(toDoId).orElseThrow(() -> new IllegalArgumentException(toDoId + "에 해당하는 ToDo가 존재하지 않습니다."));
        ToDoStatus rightStatus = toDo.getStatus();
        if(rightStatus == ToDoStatus.TODO) {
            rightStatus = ToDoStatus.WORKING;
        } else if(rightStatus == ToDoStatus.WORKING) {
            rightStatus = ToDoStatus.DONE;
        }
        toDo.setStatus(rightStatus);
    }
}
