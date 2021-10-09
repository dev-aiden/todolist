package com.aiden.dev.todolist.modules.todo;

import com.aiden.dev.todolist.modules.todo.form.AddToDoForm;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequiredArgsConstructor
public class ToDoController {

    private final ToDoService toDoService;

    @GetMapping("/")
    public String home(Model model) {
        model.addAttribute("toDoList", toDoService.getToDos(ToDoStatus.TODO));
        model.addAttribute("workingList", toDoService.getToDos(ToDoStatus.WORKING));
        model.addAttribute("doneList", toDoService.getToDos(ToDoStatus.DONE));
        return "index";
    }

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

    @PutMapping("/move-left/{toDoId}")
    public String moveLeft(@PathVariable Long toDoId) {
        toDoService.updateToDoLeft(toDoId);
        return "redirect:/";
    }

    @PutMapping("/move-right/{toDoId}")
    public String moveRight(@PathVariable Long toDoId) {
        toDoService.updateToDoRight(toDoId);
        return "redirect:/";
    }

    @GetMapping("/delete/{toDoId}")
    public String deleteToDoForm(@PathVariable Long toDoId) {
        toDoService.getToDo(toDoId);
        return "todo/delete-todo";
    }

    @DeleteMapping("/delete/{toDoId}")
    public String deleteToDo(@PathVariable Long toDoId) {
        toDoService.deleteDoTo(toDoId);
        return "redirect:/";
    }
}
