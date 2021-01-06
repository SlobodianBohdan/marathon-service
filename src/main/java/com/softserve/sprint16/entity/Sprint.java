package com.softserve.sprint16.entity;

import com.sun.istack.NotNull;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name="sprint")
public class Sprint {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @CreationTimestamp
    @Column(name = "finish")
    private LocalDate finish;

    @NotNull
    @CreationTimestamp
    @Column(name = "startDate")
    private LocalDate startDate;

    @NotNull
    @Column(name = "title")
    private String title;

    @ManyToOne
    private Marathon marathon;

    @OneToMany(mappedBy = "sprint")
    private List<Task> tasks;

    @Override
    public String toString() {
        return "Sprint{" +
                "id=" + id +
                ", finish=" + finish +
                ", start_date=" + startDate +
                ", title='" + title + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Sprint)) return false;
        Sprint sprint = (Sprint) o;
        return getTitle().equals(sprint.getTitle());
    }

    @Override
    public int hashCode() {
        return 31 * (getTitle() != null ? getTitle().hashCode() : 0);
    }
}
