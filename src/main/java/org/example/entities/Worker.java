package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "worker")
@Getter
@Setter
@RequiredArgsConstructor
public class Worker {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String Name;

    @OneToMany(mappedBy = "worker", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Task> tasks;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "worker_project",
            joinColumns = @JoinColumn(name = "worker", referencedColumnName = "id"),
            inverseJoinColumns = @JoinColumn(name = "project", referencedColumnName = "id")
    )
    private List<Project> projects;
}
