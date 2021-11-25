package pl.kamilnowak.flatrentalmanagementsystem.apartmet.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Document;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.repository.DocumentRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

@Service
@Log4j2
public class DocumentService implements CRUDOperation<Document, Long> {

    private final DocumentRepository documentRepository;
    private final PageableHelper pageableHelper;

    @Autowired
    public DocumentService(DocumentRepository documentRepository, PageableHelper pageableHelper) {
        this.documentRepository = documentRepository;
        this.pageableHelper = pageableHelper;
    }

    @Override
    public Document createObject(Document document) {
        log.debug("create document");
        return documentRepository.save(document);
    }

    @Override
    public void deleteById(Long aLong) {
        log.debug("delete document id: " + aLong);
        documentRepository.deleteById(aLong);
    }

    @Override
    public Document getObjectById(Long aLong) {
        log.debug("read document id: " + aLong);
        return documentRepository.getById(aLong);
    }

    @Override
    public Page<Document> getAllObject(int page) {
        log.debug("read documents page: " + page);
        return documentRepository.findAll(pageableHelper.countPageable(page));
    }

    @Override
    public Document updateObject(Document document, Long aLong) {
        log.debug("update document id: " + aLong);
        if (documentRepository.findById(aLong).isEmpty()) {
            return documentRepository.save(document);
        }
        document.setId(aLong);
        return documentRepository.save(document);
    }
}
