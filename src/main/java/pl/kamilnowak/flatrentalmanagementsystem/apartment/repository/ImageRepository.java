package pl.kamilnowak.flatrentalmanagementsystem.apartment.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.apartment.entity.Image;

@Repository
public interface ImageRepository extends JpaRepository<Image, Long> {
    Page<Image> getImagesByApartment_Id(Long id, Pageable pageable);
}