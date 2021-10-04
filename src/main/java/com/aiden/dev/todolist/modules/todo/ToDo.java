package com.aiden.dev.todolist.modules.todo;

import lombok.*;

import javax.persistence.*;

@Entity
@Getter @Setter @EqualsAndHashCode(of = "id")
@Builder @AllArgsConstructor @NoArgsConstructor
@Table(name = "todo")
public class ToDo {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    private String contents;

    @Enumerated(EnumType.STRING)
    private ToDoStatus status;
}
