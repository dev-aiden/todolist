package com.aiden.dev.todolist.modules.todo;

import com.aiden.dev.todolist.modules.todo.form.AddToDoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
}
