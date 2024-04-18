package org.example.entities;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "client")
@Getter
@Setter
@RequiredArgsConstructor
public class Client {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name = "name")
    private String Name;

    @OneToMany(mappedBy = "client", fetch = FetchType.EAGER, cascade = CascadeType.REMOVE)
    private List<Project> projects;
}
