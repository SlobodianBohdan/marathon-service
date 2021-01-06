package com.softserve.sprint16.entity;

import com.sun.istack.NotNull;
import lombok.*;

import javax.persistence.*;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@ToString(exclude = {"sprints", "users"})
@Table(name = "marathon")
public class Marathon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String title;

    @EqualsAndHashCode.Exclude
    @ManyToMany(cascade={CascadeType.PERSIST}, fetch = FetchType.EAGER)
    @JoinTable(
            name="marathon_user", joinColumns=@JoinColumn(name="marathon_id"),
            inverseJoinColumns=@JoinColumn(name="user_id")
    )
    private Set<User> users = new LinkedHashSet<>();

    @EqualsAndHashCode.Exclude
    @OneToMany(mappedBy = "marathon")
    private List<Sprint> sprints;
}
