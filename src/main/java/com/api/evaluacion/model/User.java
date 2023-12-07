package com.api.evaluacion.model;


import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;
    private String name;
    private String email;
    private String password;

    @OneToMany(mappedBy = "usuario", cascade = CascadeType.ALL)
    List<Phone> phones;
    private Date created;
    private Date lastLogin;
    private String token;
    private boolean active;

    public void addPhones() {
        phones.forEach(telefono -> telefono.setUsuario(this));
    }

}
