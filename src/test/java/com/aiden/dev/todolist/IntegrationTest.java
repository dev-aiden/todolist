package com.aiden.dev.todolist;

import com.aiden.dev.todolist.modules.todo.ToDo;
import com.aiden.dev.todolist.modules.todo.ToDoRepository;
import com.aiden.dev.todolist.modules.todo.ToDoService;
import com.aiden.dev.todolist.modules.todo.ToDoStatus;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.util.NestedServletException;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("통합 테스트")
public class IntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ToDoRepository toDoRepository;
    @Autowired ToDoService toDoService;

    @DisplayName("index 페이지 보이는지 테스트")
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

        assertThat(toDoRepository.findAll().size()).isEqualTo(1);
    }

    @DisplayName("이전 상태로 이동 테스트 - 존재하지 않는 ID")
    @Test
    void moveLeft_not_exist_id() throws Exception {
        // when
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(put("/move-left/1"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        });
    }

    @DisplayName("이전 상태로 이동 테스트 - TODO 상태인 경우 상태 유지")
    @Test
    void moveLeft_todo() throws Exception {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.TODO)
                .build();
        ToDo savedToDo = toDoRepository.save(toDo);

        // when
        mockMvc.perform(put("/move-left/" + savedToDo.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // then
        assertThat(toDoRepository.findById(savedToDo.getId()).get().getStatus()).isEqualTo(ToDoStatus.TODO);
    }

    @DisplayName("이전 상태로 이동 테스트")
    @Test
    void moveLeft() throws Exception {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.DONE)
                .build();
        ToDo savedToDo = toDoRepository.save(toDo);

        // when
        mockMvc.perform(put("/move-left/" + savedToDo.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // then
        assertThat(toDoRepository.findById(savedToDo.getId()).get().getStatus()).isEqualTo(ToDoStatus.WORKING);
    }

    @DisplayName("다음 상태로 이동 테스트 - 존재하지 않는 ID")
    @Test
    void moveRight_not_exist_id() throws Exception {
        // when
        assertThrows(NestedServletException.class, () -> {
            mockMvc.perform(put("/move-right/1"))
                    .andDo(print())
                    .andExpect(status().is3xxRedirection())
                    .andExpect(redirectedUrl("/"));
        });
    }

    @DisplayName("다음 상태로 이동 테스트 - DONE 상태인 경우 상태 유지")
    @Test
    void moveRight_done() throws Exception {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.DONE)
                .build();
        ToDo savedToDo = toDoRepository.save(toDo);

        // when
        mockMvc.perform(put("/move-right/" + savedToDo.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // then
        assertThat(toDoRepository.findById(savedToDo.getId()).get().getStatus()).isEqualTo(ToDoStatus.DONE);
    }

    @DisplayName("다음 상태로 이동 테스트")
    @Test
    void moveRight() throws Exception {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.TODO)
                .build();
        ToDo savedToDo = toDoRepository.save(toDo);

        // when
        mockMvc.perform(put("/move-right/" + savedToDo.getId()))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("/"));

        // then
        assertThat(toDoRepository.findById(savedToDo.getId()).get().getStatus()).isEqualTo(ToDoStatus.WORKING);
    }
}
