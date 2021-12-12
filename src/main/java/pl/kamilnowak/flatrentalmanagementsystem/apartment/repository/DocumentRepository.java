package pl.kamilnowak.flatrentalmanagementsystem.apartment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Document;

import java.util.List;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
    Page<Document> getDocumentsByTenant_Id(Long id, Pageable pageable);
    List<Document> getDocumentsByTenant_Id(Long id);
}
