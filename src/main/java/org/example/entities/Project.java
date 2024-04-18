package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.Calendar;
import java.util.List;

@Entity
@Table(name = "project")
@Getter
@Setter
@RequiredArgsConstructor
public class Project {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "data")
    private String data;
    @Column(name = "status")
    private String status;
    @Column(name = "date")
    private Calendar date;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "client", referencedColumnName = "id")
    private Client client;

    @ManyToMany(mappedBy = "projects", fetch = FetchType.EAGER)
    private List<Worker> workers;

    @OneToMany(mappedBy = "project", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Task> tasks;
}
