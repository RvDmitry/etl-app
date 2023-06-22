package my.home.etlapp.repository;

import my.home.etlapp.entity.BusinessEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface BusinessRepository extends JpaRepository<BusinessEntity, Long> {

    @Query("select b from BusinessEntity b where b.parentId = ?1")
    Optional<BusinessEntity> findByParentId(Long parentId);

    void deleteByParentId(Long parentId);

}
