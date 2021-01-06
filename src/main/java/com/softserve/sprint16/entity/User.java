package com.softserve.sprint16.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;
import java.util.Set;

@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "users")
@Getter
@Setter
@ToString
public class User {
    public enum Role {
        MENTOR, TRAINEE
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // @Pattern(regexp = ".+@.+\\..+", message = "Please provide a valid email address")
    @Pattern(regexp = "[_a-zA-Z0-9\\-]+@[a-z]+.[a-z]{2,3}", message = "Please provide a valid email address")
    private String email;

    @Size(min = 2, max = 20, message = "First name must be between 2 and 20 characters")
    private String firstName;


    @Size(min = 2, max = 20, message = "Last name must be between 2 and 20 characters")
    private String lastName;

    @NotNull
    private String password;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Role role;

    @ToString.Exclude
    @ManyToMany(mappedBy = "users")
    private Set<Marathon> marathons;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", firstName='" + firstName + '\'' +
                ", lastName='" + lastName + '\'' +
                ", password='" + password + '\'' +
                ", role=" + role +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return getEmail().equals(user.getEmail()) &&
                getFirstName().equals(user.getFirstName()) &&
                getLastName().equals(user.getLastName()) &&
                getPassword().equals(user.getPassword()) &&
                getRole() == user.getRole();
    }

    @Override
    public int hashCode() {
        return 31 * (int) (getId() +
                +(getEmail() != null ? getEmail().hashCode() : 0) +
                +(getFirstName() != null ? getFirstName().hashCode() : 0) +
                +(getLastName() != null ? getLastName().hashCode() : 0) +
                +(getPassword() != null ? getPassword().hashCode() : 0) +
                +(getRole() != null ? getRole().hashCode() : 0));
    }
}
