package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

@Entity
@Table(name = "task")
@Getter
@Setter
@RequiredArgsConstructor
public class Task {
    @Id
    @Column(name="id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="data")
    private String data;


    @ManyToOne
    @JoinColumn(name="project", referencedColumnName = "id")
    private Project project;

    @ManyToOne
    @JoinColumn(name="worker", referencedColumnName = "id")
    private Worker worker;
}