package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Getter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Comment {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private Long id; // 대표키

    @ManyToOne
    @JoinColumn(name="article_id") // FK
    private Article article; // 해당 댓글의 부모 게시글

    @Column
    private String nickname;

    @Column
    private String body;
}
