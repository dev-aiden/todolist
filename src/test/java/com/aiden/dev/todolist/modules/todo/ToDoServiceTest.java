package com.aiden.dev.todolist.modules.todo;

import com.aiden.dev.todolist.modules.todo.form.AddToDoForm;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
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

    @DisplayName("상태로 할 일 목록 조회 테스트")
    @Test
    void getToDos() {
        // when
        toDoService.getToDos(ToDoStatus.TODO);

        // then
        verify(toDoRepository).findByStatus(any(ToDoStatus.class));
    }

    @DisplayName("이전 상태로 이동 테스트 - 존재하지 않는 ID")
    @Test
    void updateToDoLeft_not_exist_id() {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.DONE)
                .build();

        // when, then
        assertThrows(IllegalArgumentException.class, () -> toDoService.updateToDoLeft(1L));
    }

    @DisplayName("이전 상태로 이동 테스트")
    @Test
    void updateToDoLeft() {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.DONE)
                .build();

        given(toDoRepository.findById(any())).willReturn(Optional.of(toDo));

        // when, then
        toDoService.updateToDoLeft(1L);
        assertThat(toDo.getStatus()).isEqualTo(ToDoStatus.WORKING);
        toDoService.updateToDoLeft(1L);
        assertThat(toDo.getStatus()).isEqualTo(ToDoStatus.TODO);
        toDoService.updateToDoLeft(1L);
        assertThat(toDo.getStatus()).isEqualTo(ToDoStatus.TODO);
    }

    @DisplayName("다음 상태로 이동 테스트 - 존재하지 않는 ID")
    @Test
    void updateToDoRight_not_exist_id() {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.DONE)
                .build();

        // when, then
        assertThrows(IllegalArgumentException.class, () -> toDoService.updateToDoRight(1L));
    }

    @DisplayName("다음 상태로 이동 테스트")
    @Test
    void updateToDoRight() {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.TODO)
                .build();

        given(toDoRepository.findById(any())).willReturn(Optional.of(toDo));

        // when, then
        toDoService.updateToDoRight(1L);
        assertThat(toDo.getStatus()).isEqualTo(ToDoStatus.WORKING);
        toDoService.updateToDoRight(1L);
        assertThat(toDo.getStatus()).isEqualTo(ToDoStatus.DONE);
        toDoService.updateToDoRight(1L);
        assertThat(toDo.getStatus()).isEqualTo(ToDoStatus.DONE);
    }

    @DisplayName("할 일 삭제 테스트 - 존재하지 않는 ID")
    @Test
    void deleteToDo_not_exist_id() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> toDoService.deleteDoTo(1L));
    }

    @DisplayName("할 일 삭제 테스트")
    @Test
    void deleteToDo() {
        // given
        ToDo toDo = ToDo.builder()
                .title("title")
                .contents("contents")
                .status(ToDoStatus.TODO)
                .build();

        given(toDoRepository.findById(any())).willReturn(Optional.of(toDo));

        // when
        toDoService.deleteDoTo(1L);

        // then
        verify(toDoRepository).delete(any(ToDo.class));
    }
    
    @DisplayName("할 일 가져오기 테스트 - 존재하지 않는 경우")
    @Test
    void getToDo_not_exist() {
        // when, then
        assertThrows(IllegalArgumentException.class, () -> toDoService.getToDo(1L));
    }
}