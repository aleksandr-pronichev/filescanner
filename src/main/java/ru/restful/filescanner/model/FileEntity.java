package ru.restful.filescanner.model;

import jakarta.persistence.*;
import lombok.Data;
import org.hibernate.annotations.CreationTimestamp;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "files")
public class FileEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(name = "content_type")
    private String contentType;

    private Long size;

    @Lob
    @Column(nullable = false)
    private byte[] content;

    @CreationTimestamp
    @Column(name = "upload_date")
    private LocalDateTime uploadDate;
}