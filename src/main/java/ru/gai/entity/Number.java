package ru.gai.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name = "number")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Number {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "word")
    private String word;

    @Column(name = "numeric")
    private Integer numeric;

    public Number(String word, Integer numeric) {
        this.word = word;
        this.numeric = numeric;
    }
}
