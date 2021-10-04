package com.aiden.dev.todolist.modules.todo;

import com.aiden.dev.todolist.modules.todo.form.AddToDoForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class ToDoServiceTest {

    @InjectMocks ToDoService toDoService;
    @Mock ToDoRepository toDoRepository;

    @DisplayName("할 일 추가 테스트")
    @Test
    void addToDo() {
        // when
        toDoService.addToDo(new AddToDoForm());

        // then
        verify(toDoRepository).save(any(ToDo.class));
    }
}