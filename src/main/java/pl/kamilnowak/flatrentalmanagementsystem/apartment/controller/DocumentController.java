package pl.kamilnowak.flatrentalmanagementsystem.apartment.controller;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.dto.DocumentDTO;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Document;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Tenant;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.DocumentService;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.service.TenantService;
import pl.kamilnowak.flatrentalmanagementsystem.exception.NotAuthorizationException;

import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/document")
public class DocumentController {

    private final DocumentService documentService;
    private final ModelMapper modelMapper;
    private final TenantService tenantService;

    @Autowired
    public DocumentController(DocumentService documentService, ModelMapper modelMapper, TenantService tenantService) {
        this.documentService = documentService;
        this.modelMapper = modelMapper;
        this.tenantService = tenantService;
    }

    @GetMapping("/{id}")
    public ResponseEntity<DocumentDTO> getDocument(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Document document = documentService.getDocumentByLoginUserMailAndId(principal.getName(), id);
        if (document == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(document, DocumentDTO.class));
    }

    @PostMapping("")
    public ResponseEntity<DocumentDTO> createDocument(@RequestBody Document document) {
        Document createdDocument = documentService.createObject(document);
        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(modelMapper.map(createdDocument, DocumentDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteDocument(@PathVariable Long id, Principal principal) throws NotAuthorizationException  {
        Document documentToCheck = documentService.getDocumentByLoginUserMailAndId(principal.getName(), id);
        if (documentToCheck == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<DocumentDTO> updateDocument(@PathVariable Long id, @RequestBody Document document, Principal principal) throws NotAuthorizationException  {
        Document documentToCheck = documentService.getDocumentByLoginUserMailAndId(principal.getName(), id);
        if (documentToCheck == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(modelMapper.map(documentService.updateObject(document, id), DocumentDTO.class));
    }

    @GetMapping("/tenant/{id}")
    public ResponseEntity<Page<DocumentDTO>> getDocumentsByTenant(@PathVariable Long id, @RequestParam(defaultValue = "1") int page, Principal principal) throws NotAuthorizationException  {
        Tenant tenant = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenant == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(documentService.getObjectsByTenantId(id, page)
                .map(document -> modelMapper.map(document, DocumentDTO.class)));
    }

    @GetMapping("/tenant/{id}/all")
    public ResponseEntity<List<DocumentDTO>> getDocumentsByTenant(@PathVariable Long id, Principal principal) throws NotAuthorizationException {
        Tenant tenant = tenantService.getTenantByLoginUserMailAndId(principal.getName(), id);
        if (tenant == null) {
            throw new NotAuthorizationException();
        }
        return ResponseEntity.ok(documentService.getObjectsByTenantId(id).stream()
                .map(document -> modelMapper.map(document, DocumentDTO.class))
                .collect(Collectors.toList()));
    }
}
