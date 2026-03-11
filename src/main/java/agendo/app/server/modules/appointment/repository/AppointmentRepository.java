package agendo.app.server.modules.appointment.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import agendo.app.server.modules.appointment.models.AppointmentEntity;
import agendo.app.server.modules.user.models.UserEntity;

public interface AppointmentRepository extends JpaRepository<AppointmentEntity, Long> {

    @Query("SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.professional " +
            "JOIN FETCH a.client " +
            "JOIN FETCH a.serviceType " +
            "WHERE a.professional = :user OR a.client = :user")
    List<AppointmentEntity> findByParticipant(@Param("user") UserEntity user);
    // retorna todos os agendamentos independente do usuario ser profissional ou cliente

    @Query("SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.professional " +
            "JOIN FETCH a.client " +
            "JOIN FETCH a.serviceType " +
            "WHERE a.professional = :user")
    List<AppointmentEntity> findByProfessional(@Param("user") UserEntity user);

    @Query("SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.professional " +
            "JOIN FETCH a.client " +
            "JOIN FETCH a.serviceType " +
            "WHERE a.client = :user")
    List<AppointmentEntity> findByClient(@Param("user") UserEntity user);

    @Query("SELECT a FROM AppointmentEntity a " +
            "JOIN FETCH a.professional " +
            "JOIN FETCH a.client " +
            "JOIN FETCH a.serviceType " +
            "WHERE a.id = :id AND (a.professional = :user OR a.client = :user)")
    Optional<AppointmentEntity> findByIdAndParticipant(@Param("id") Long id, @Param("user") UserEntity user);
    // retorna o agendamento do ID especificado e valida (se o user nao estiver relacionado ao agendamento retorna erro)
}
