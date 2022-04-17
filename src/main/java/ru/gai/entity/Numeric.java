package ru.gai.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "numeric")
@Getter
@Setter
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

    @Column(name = "issued")
    private Boolean issued;

    @Column(name = "next")
    private Boolean next;
}
