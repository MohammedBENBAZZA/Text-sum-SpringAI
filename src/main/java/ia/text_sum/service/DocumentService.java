package ia.text_sum.service;

import ia.text_sum.entity.Document;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public interface DocumentService {
    Document saveAndSummarize(MultipartFile file) throws Exception;
    List<Document> getAllDocuments();
    Document getDocumentById(Long id) throws Exception;
}
