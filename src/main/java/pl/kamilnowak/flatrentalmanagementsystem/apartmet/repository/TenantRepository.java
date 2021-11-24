package pl.kamilnowak.flatrentalmanagementsystem.apartmet.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import pl.kamilnowak.flatrentalmanagementsystem.apartmet.entity.Tenant;

@Repository
public interface TenantRepository extends JpaRepository<Tenant, Long> {
}
