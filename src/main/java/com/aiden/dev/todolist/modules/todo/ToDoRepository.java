package com.aiden.dev.todolist.modules.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional(readOnly = true)
public interface ToDoRepository extends JpaRepository<ToDo, Long> {

    List<ToDo> findByStatus(ToDoStatus status);
}
