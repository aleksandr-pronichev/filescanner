package ru.restful.filescanner.service;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import ru.restful.filescanner.model.FileEntity;
import ru.restful.filescanner.repository.FileRepository;

import java.io.IOException;

@Service
public class FileService {
    private final FileRepository fileRepository;
    private final SignatureScanService signatureScanService;

    public FileService(FileRepository fileRepository, SignatureScanService signatureScanService) {
        this.fileRepository = fileRepository;
        this.signatureScanService = signatureScanService;
    }

    public FileEntity saveFile(MultipartFile file) throws IOException {
        FileEntity fileEntity = new FileEntity();
        fileEntity.setName(file.getOriginalFilename());
        fileEntity.setContentType(file.getContentType());
        fileEntity.setSize(file.getSize());
        byte[] fileContent = file.getBytes();

        if (!signatureScanService.isFileSafe(fileContent)) {
            throw new IOException("File contains known malicious signature!");
        }

        fileEntity.setContent(fileContent);
        return fileRepository.save(fileEntity);
    }

    public FileEntity getFileByName(String fileName) {
        return fileRepository.findByName(fileName);
    }

    public FileEntity getFileById(Long fileId) {
        return fileRepository.findById(fileId).orElse(null);
    }
}
