package pl.kamilnowak.flatrentalmanagementsystem.apartmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Document;

@Repository
public interface DocumentRepository extends JpaRepository<Document, Long> {
}
