package ru.rti.kettu.sbjava.model.db.h2;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table
public class Album implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(updatable = false, nullable = false)
    private Long id;
    @Column(length = 50)
    private String author;
    @Column(length = 50)
    private String name;
    @Column(length = 4)
    private Integer year;

}
