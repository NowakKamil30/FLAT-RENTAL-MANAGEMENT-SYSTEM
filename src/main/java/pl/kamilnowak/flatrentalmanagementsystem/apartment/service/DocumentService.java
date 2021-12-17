package pl.kamilnowak.flatrentalmanagementsystem.apartment.service;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Document;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.repository.DocumentRepository;
import pl.kamilnowak.flatrentalmanagementsystem.service.CRUDOperation;
import pl.kamilnowak.flatrentalmanagementsystem.service.PageableHelper;

import java.util.List;

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

    public Page<Document> getObjectsByTenantId(Long aLong, int page) {
        log.debug("gets all documents by tenant id: " + aLong);
        return documentRepository.getDocumentsByTenant_Id(aLong, pageableHelper.countPageable(page));
    }

    public List<Document> getObjectsByTenantId(Long aLong) {
        log.debug("gets all documents by tenant id: " + aLong);
        return documentRepository.getDocumentsByTenant_Id(aLong);
    }

    public void deleteAllByTenantId(Long aLong) {
        log.debug("delete all document by tenant: " + aLong);
        documentRepository.deleteAllByTenant_Id(aLong);
    }

    public Document getDocumentByLoginUserMailAndId(String mail, Long id) {
        log.debug("get document by login user mail: " + mail + " and document id: " + id);
        return documentRepository.getDocumentByTenant_Apartment_UserData_LoginUser_MailAndId(mail, id);
    }
}
