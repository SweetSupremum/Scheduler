package com.Scheduled.Scheduled_server.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.List;

import static javax.persistence.CascadeType.ALL;
import static javax.persistence.EnumType.STRING;
import static javax.persistence.FetchType.LAZY;
import static javax.persistence.GenerationType.IDENTITY;

@Entity
@NoArgsConstructor
@Getter
@Setter
public class Customer {
    @Id
    @GeneratedValue(strategy = IDENTITY)
    private Long id;
    private String userName;
    private String password;

    @Enumerated(STRING)
    private Role role;
    @Enumerated(STRING)
    private Status status;

    @OneToMany(cascade = ALL, mappedBy = "customer", fetch = LAZY)
    private List<GameLibrary> gameLibraries;

}
