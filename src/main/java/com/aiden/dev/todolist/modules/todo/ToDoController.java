package com.aiden.dev.todolist.modules.todo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class ToDoController {

    @GetMapping("/add")
    public String addToDoForm() {
        return "/todo/add-todo";
    }
}
