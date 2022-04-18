package ru.gai.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "numeric")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Numeric {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;

    @Column(name = "numeric")
    private Integer numeric;

    @Column(name = "date_issued")
    private LocalDateTime dateIssued;
}
