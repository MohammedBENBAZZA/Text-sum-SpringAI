package ia.text_sum.service;

import ia.text_sum.entity.Document;
import ia.text_sum.repository.DocumentRepository;
import jakarta.transaction.Transactional;
import org.apache.tika.Tika;
import org.springframework.ai.chat.model.ChatResponse;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.openai.OpenAiChatModel;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Service
@Transactional
public class DocumentServiceImpl implements DocumentService {

    private final DocumentRepository documentRepository;
    private final OpenAiChatModel chatModel;
    private final Tika tika;

    public DocumentServiceImpl(DocumentRepository documentRepository, OpenAiChatModel chatModel, Tika tika) {
        this.documentRepository = documentRepository;
        this.chatModel = chatModel;
        this.tika = tika;
    }


    @Override
    public Document saveAndSummarize(MultipartFile file) throws Exception {
        String content = tika.parseToString(file.getInputStream());

        ChatResponse response = chatModel.call(
                new Prompt("Fais un résumé court de ce texte : " + content)
        );
        String summary = response.getResult().getOutput().getText();


        Document doc = new Document();
        doc.setFilename(file.getOriginalFilename());
        doc.setContent(content);
        doc.setSummary(summary);

        return documentRepository.save(doc);
    }

    @Override
    public List<Document> getAllDocuments() {
        return documentRepository.findAll();
    }

    @Override
    public Document getDocumentById(Long id) throws Exception {
        return documentRepository.findById(id).orElseThrow(() -> new Exception("Document not found"));
    }
}
