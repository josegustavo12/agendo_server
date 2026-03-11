package agendo.app.server.modules.user.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agendo.app.server.modules.user.models.ProfessionalProfileEntity;
import agendo.app.server.modules.user.models.UserEntity;

public interface ProfessionalProfileRepository extends JpaRepository<ProfessionalProfileEntity, Long> {
    Optional<ProfessionalProfileEntity> findByUser(UserEntity user);

    Optional<ProfessionalProfileEntity> findByUserId(Long userId);

    @Query("""
        SELECT p FROM ProfessionalProfileEntity p
        JOIN FETCH p.user u
        LEFT JOIN FETCH p.profession pr
        WHERE (:name IS NULL OR LOWER(u.name) LIKE LOWER(CONCAT('%', CAST(:name AS string), '%')))
        AND (:professionId IS NULL OR pr.id = :professionId)
        AND (:serviceTypeName IS NULL OR EXISTS (
            SELECT st FROM ServiceTypeEntity st
            WHERE st.owner = u AND LOWER(st.name) LIKE LOWER(CONCAT('%', CAST(:serviceTypeName AS string), '%'))
        ))
    """)
    List<ProfessionalProfileEntity> searchProfessionals(
        @Param("name") String name,
        @Param("professionId") Long professionId,
        @Param("serviceTypeName") String serviceTypeName
    );
}
