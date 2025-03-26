package ru.restful.filescanner.controller;

import jakarta.transaction.Transactional;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.restful.filescanner.model.FileEntity;
import ru.restful.filescanner.service.FileService;

import java.io.IOException;

@RestController
@RequestMapping("/api/files")
public class FileController {
    private final FileService fileService;

    public FileController(FileService fileService) {
        this.fileService = fileService;
    }

    @PostMapping(consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<?> uploadFile(@RequestParam("file") MultipartFile file) throws IOException {
        if (file.isEmpty()) {
            return ResponseEntity.badRequest().body("File is empty");
        }

        try {
            FileEntity savedFile = fileService.saveFile(file);
            return ResponseEntity.ok(savedFile.getId());
        } catch (IOException e) {
            return ResponseEntity.internalServerError().body("Failed to process file");
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<byte[]> getFileById(@PathVariable Long id) {
        FileEntity fileEntity = fileService.getFileById(id);
        if (fileEntity == null) {
            return ResponseEntity.notFound().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(fileEntity.getContentType()))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(fileEntity.getContent());
    }

    @Transactional
    @GetMapping("/by-name/{name}")
    public ResponseEntity<byte[]> getFileByName(@PathVariable String name) {
        FileEntity fileEntity = fileService.getFileByName(name);
        if (fileEntity == null) {
            return ResponseEntity.notFound().build();
        }

        String contentType = fileEntity.getContentType();
        if (contentType == null || contentType.isBlank()) {
            contentType = "application/octet-stream";
        }

        byte[] content = fileEntity.getContent();
        if (content == null || content.length == 0) {
            return ResponseEntity.noContent().build();
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION,
                        "attachment; filename=\"" + fileEntity.getName() + "\"")
                .body(content);
    }

}