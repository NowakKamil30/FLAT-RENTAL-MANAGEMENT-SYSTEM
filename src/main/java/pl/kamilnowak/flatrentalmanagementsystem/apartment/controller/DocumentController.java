package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.DocumentDHO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Document;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.DocumentService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotFoundException;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {

    private final DocumentService documentService;
    private final ModelMapper modelMapper;

    @Autowired
    public DocumentController(DocumentService documentService, ModelMapper modelMapper) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("")
    public ResponseEntity<Page<DocumentDHO>> getDocuments(@RequestParam(defaultValue = "1") int page) {
        Page<DocumentDHO> documentDHOPage = documentService.getAllObject(page)
                .map(document -> modelMapper.map(document, DocumentDHO.class));
        return ResponseEntity.ok(documentDHOPage);
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDHO> getDocument(@PathVariable Long id) {
        Document document = documentService.getObjectById(id);
        if (document == null) {
            throw new NotFoundException(id.toString());
        }
        return ResponseEntity.ok(modelMapper.map(document, DocumentDHO.class));
    }

    @PostMapping("")
    public ResponseEntity<DocumentDHO> createDocument(@RequestBody Document document) {
        Document createdDocument = documentService.createObject(document);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdDocument, DocumentDHO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id) {
        if (documentService.getObjectById(id) == null) {
            throw new NotFoundException(id.toString());
        }
        documentService.deleteById(id);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDHO> updateDocument(@PathVariable Long id, @RequestBody Document document) {
        return ResponseEntity.ok(modelMapper.map(documentService.updateObject(document, id), DocumentDHO.class));
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<Page<DocumentDHO>> getDocumentsByTenant(@PathVariable Long id, @RequestParam(defaultValue = "1") int page) {
        return ResponseEntity.ok(documentService.getObjectsByTenantId(id, page)
                .map(document -> modelMapper.map(document, DocumentDHO.class)));
    }

    @GetMapping("/tenant/{id}/all")
    public ResponseEntity<List<DocumentDHO>> getDocumentsByTenant(@PathVariable Long id) {
        return ResponseEntity.ok(documentService.getObjectsByTenantId(id).stream()
                .map(document -> modelMapper.map(document, DocumentDHO.class))
                .collect(Collectors.toList()));
    }
}
