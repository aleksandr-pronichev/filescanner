package ru.restful.filescanner.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.restful.filescanner.model.FileEntity;
import ru.restful.filescanner.repository.FileRepository;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepository fileRepository;

    public FileService(FileRepository fileRepository) {
        this.fileRepository = fileRepository;
    }

    public FileEntity saveFile(MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setContentType(file.getContentType());
        fileEntity.setSize(file.getSize());
        fileEntity.setContent(file.getBytes());

        return fileRepository.save(fileEntity);
    }

    public FileEntity getFileByName(String fileName) {
        return fileRepository.findByName(fileName);
    }

    public FileEntity getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }
}
