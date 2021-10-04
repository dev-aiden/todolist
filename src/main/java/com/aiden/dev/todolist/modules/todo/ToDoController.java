package com.aiden.dev.todolist.modules.todo;

import com.aiden.dev.todolist.modules.todo.form.AddToDoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping("/add")
    public String addToDoForm(Model model) {
        model.addAttribute(new AddToDoForm());
        return "todo/add-todo";
    }

    @PostMapping("/add")
    public String addToDo(AddToDoForm addToDoForm) {
        toDoService.addToDo(addToDoForm);
        return "redirect:/";
    }
}
