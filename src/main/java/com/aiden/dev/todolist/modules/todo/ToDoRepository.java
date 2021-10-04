package com.aiden.dev.todolist.modules.todo;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.transaction.annotation.Transactional;

@Transactional(readOnly = true)
public interface ToDoRepository extends JpaRepository<ToDo, Long> {
}
