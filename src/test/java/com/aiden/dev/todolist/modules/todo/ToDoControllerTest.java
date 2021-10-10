package com.aiden.dev.todolist.modules.todo;

import com.aiden.dev.todolist.modules.todo.form.AddToDoForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(ToDoController.class)
class ToDoControllerTest {

    @Autowired MockMvc mockMvc;
    @MockBean ToDoService toDoService;

    @DisplayName("index 페이지 테스트")
    @Test
    void home() throws Exception {
        mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("index"))
                .andExpect(model().attributeExists("toDoList"))
                .andExpect(model().attributeExists("workingList"))
                .andExpect(model().attributeExists("doneList"));
    }

    @DisplayName("할 일 추가 페이지 보이는지 테스트")
    @Test
    void addToDoForm() throws Exception {
        mockMvc.perform(get("/add"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todo/add-todo"))
                .andExpect(model().attributeExists("addToDoForm"));
    }

    @DisplayName("할 일 추가 테스트")
    @Test
    void addToDo() throws Exception {
        mockMvc.perform(post("/add")
                        .param("title", "title")
                        .param("contents", "contents"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(toDoService, times(1)).addToDo(any());
    }

    @DisplayName("이전 상태로 이동 테스트")
    @Test
    void moveLeft() throws Exception {
        mockMvc.perform(put("/move-left/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(toDoService).updateToDoLeft(1L);
    }

    @DisplayName("다음 상태로 이동 테스트")
    @Test
    void moveRight() throws Exception {
        mockMvc.perform(put("/move-right/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(toDoService).updateToDoRight(1L);
    }

    @DisplayName("할 일 삭제 페이지 보이는지 테스트")
    @Test
    void deleteToDoForm() throws Exception {
        mockMvc.perform(get("/delete/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todo/delete-todo"));

        verify(toDoService).getToDo(1L);
    }

    @DisplayName("할 일 삭제 테스트")
    @Test
    void deleteToDo() throws Exception {
        mockMvc.perform(delete("/delete/1"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(toDoService).deleteDoTo(1L);
    }

    @DisplayName("할 일 수정 페이지 보이는지 테스트")
    @Test
    void editToDoForm() throws Exception {
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .build();

        given(toDoService.getToDo(any())).willReturn(toDo);

        mockMvc.perform(get("/edit/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(view().name("todo/edit-todo"))
                .andExpect(model().attributeExists("addToDoForm"));

        verify(toDoService).getToDo(1L);
    }

    @DisplayName("할 일 수정 테스트")
    @Test
    void editToDo() throws Exception {
        AddToDoForm addToDoForm = new AddToDoForm();
        addToDoForm.setTitle("title");
        addToDoForm.setContents("contents");

        mockMvc.perform(put("/edit/1")
                        .param("title", addToDoForm.getTitle())
                        .param("contents", addToDoForm.getContents()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        verify(toDoService).updateToDo(1L, addToDoForm);
    }
}