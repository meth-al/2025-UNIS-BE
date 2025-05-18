package com.example.firstproject.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@AllArgsConstructor
@NoArgsConstructor
@ToString
@Getter
@Entity
@Table(name="Article")
public class Article {
    @Id
    // JPA가 자동 생성, DDL을 통해 SQL로 직접 설정시 AUTO_INCREMENT 쓰는 것과 동일
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id") // 기본적으로 생략해도 무방하지만 명시 가능
    private Long id;

    @Column(name="title")
    private String title;

    @Column(name="content")
    private String content;

}
