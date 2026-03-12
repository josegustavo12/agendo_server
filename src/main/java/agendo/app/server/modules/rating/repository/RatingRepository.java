package agendo.app.server.modules.rating.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agendo.app.server.modules.rating.models.RatingEntity;
import agendo.app.server.modules.user.models.UserEntity;

public interface RatingRepository extends JpaRepository<RatingEntity, Long> {

    List<RatingEntity> findByProfessional(UserEntity professional);

    List<RatingEntity> findByProfessionalId(Long professionalId);

    List<RatingEntity> findByClient(UserEntity client);

    @Query("SELECT AVG(r.score) FROM RatingEntity r WHERE r.professional.id = :professionalId")
    Double getAverageScoreByProfessionalId(@Param("professionalId") Long professionalId);

    @Query("SELECT AVG(r.score) FROM RatingEntity r WHERE r.professional = :professional")
    Double getAverageScoreByProfessional(@Param("professional") UserEntity professional);

    @Query("SELECT COUNT(r) FROM RatingEntity r WHERE r.professional.id = :professionalId")
    Long countByProfessionalId(@Param("professionalId") Long professionalId);
}

