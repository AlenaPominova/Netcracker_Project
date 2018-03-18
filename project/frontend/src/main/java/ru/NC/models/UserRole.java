package ru.NC.models;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Set;

@Entity
@Table(name = "ROLE")
public class UserRole implements Serializable {

    private Long id;
    private String name;

    public UserRole() {
    }

    public UserRole(Long id) {
        this.id = id;
    }

    @Id
    @Column(name = "ROLE_ID", unique = true, nullable = false, precision = 5, scale = 0)
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Column(name = "ROLE_NAME", unique = true, nullable = false, precision = 5, scale = 0)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public String toString() {
        return UserRole.class.getSimpleName() +
                "{id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
