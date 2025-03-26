package ru.restful.filescanner.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ru.restful.filescanner.model.FileEntity;

public interface FileRepository extends JpaRepository<FileEntity, Long> {
    FileEntity findByName(String name);
}
