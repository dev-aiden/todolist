package com.aiden.dev.todolist.modules.todo;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import static org.assertj.core.api.Assertions.assertThat;

@DataJpaTest
class ToDoRepositoryTest {

    @Autowired ToDoRepository toDoRepository;

    @BeforeEach
    void beforeEach() {
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.TODO)
                .build();
        toDoRepository.save(toDo);
    }

    @DisplayName("상태로 목록조회 테스트")
    @Test
    void findByStatus() {
        assertThat(toDoRepository.findByStatus(ToDoStatus.TODO).size()).isEqualTo(1);
        assertThat(toDoRepository.findByStatus(ToDoStatus.WORKING).size()).isEqualTo(0);
        assertThat(toDoRepository.findByStatus(ToDoStatus.DONE).size()).isEqualTo(0);
    }
}