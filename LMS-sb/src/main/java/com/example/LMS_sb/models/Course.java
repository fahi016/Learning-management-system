package com.example.LMS_sb.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "courses")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "title",nullable = false)
    private String title;

    @Column(name = "description")
    private String description;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    @ManyToOne(optional = false,fetch = FetchType.LAZY)
    @JoinColumn(
            name = "teacher_id",
            nullable = false
    )
    private Teacher teacher;

    @PrePersist
    public void onCreate(){
        this.createdAt = LocalDateTime.now();
    }
}
