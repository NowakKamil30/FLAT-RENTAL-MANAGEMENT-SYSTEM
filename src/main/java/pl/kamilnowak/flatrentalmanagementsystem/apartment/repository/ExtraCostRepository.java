package pl.kamilnowak.flatrentalmanagementsystem.apartment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.ExtraCost;

import java.util.List;

@Repository
public interface ExtraCostRepository extends JpaRepository<ExtraCost, Long> {
    List<ExtraCost> getAllByTenant_Id(Long id);
    Page<ExtraCost> getAllByTenant_Id(Long id, Pageable pageable);
}
