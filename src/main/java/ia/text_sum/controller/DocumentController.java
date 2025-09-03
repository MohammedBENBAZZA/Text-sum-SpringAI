package ia.text_sum.controller;

import ia.text_sum.entity.Document;
import ia.text_sum.service.DocumentService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/documents")
public class DocumentController {
    private final DocumentService documentService;

    public DocumentController(DocumentService documentService) {
        this.documentService = documentService;
    }

    @PostMapping
    public ResponseEntity<Document> upload(@RequestParam("file") MultipartFile file) throws Exception {
        Document savedDoc = documentService.saveAndSummarize(file);
        return ResponseEntity.status(201).body(savedDoc);
    }

    @GetMapping
    public ResponseEntity<List<Document>> getAll() {
        List<Document> documents = documentService.getAllDocuments();
        return ResponseEntity.ok(documents);

    }

    @GetMapping("/{id}")
    public ResponseEntity<Document> getById(@PathVariable Long id) throws Exception {
        Document doc = documentService.getDocumentById(id);
        return ResponseEntity.ok(doc);
    }

}
