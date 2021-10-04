package com.aiden.dev.todolist;

import com.aiden.dev.todolist.modules.todo.ToDoRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@Transactional
@AutoConfigureMockMvc
@DisplayName("통합 테스트")
public class IntegrationTest {

    @Autowired MockMvc mockMvc;
    @Autowired ToDoRepository toDoRepository;

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
}
