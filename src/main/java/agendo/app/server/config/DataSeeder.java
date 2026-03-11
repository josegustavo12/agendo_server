package agendo.app.server.config;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import agendo.app.server.modules.appointment.models.AppointmentEntity;
import agendo.app.server.modules.appointment.models.ServiceTypeEntity;
import agendo.app.server.modules.appointment.repository.AppointmentRepository;
import agendo.app.server.modules.appointment.repository.ServiceTypeRepository;
import agendo.app.server.modules.user.models.ClientProfileEntity;
import agendo.app.server.modules.user.models.ProfessionalProfileEntity;
import agendo.app.server.modules.user.models.ProfessionEntity;
import agendo.app.server.modules.user.models.UserEntity;
import agendo.app.server.modules.user.models.UserRole;
import agendo.app.server.modules.user.repository.ClientProfileRepository;
import agendo.app.server.modules.user.repository.ProfessionalProfileRepository;
import agendo.app.server.modules.user.repository.ProfessionRepository;
import agendo.app.server.modules.user.repository.UserRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Component
@RequiredArgsConstructor
@Slf4j
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProfessionRepository professionRepository;
    private final ProfessionalProfileRepository professionalProfileRepository;
    private final ClientProfileRepository clientProfileRepository;
    private final ServiceTypeRepository serviceTypeRepository;
    private final AppointmentRepository appointmentRepository;
    private final PasswordEncoder passwordEncoder;
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void run(String... args) {
        log.info("=== Iniciando seed do banco de dados ===");

        // 1. Limpar tudo (deleteAllInBatch executa DELETE direto no banco, sem carregar entidades)
        log.info("Excluindo todos os dados...");
        appointmentRepository.deleteAllInBatch();
        serviceTypeRepository.deleteAllInBatch();
        professionalProfileRepository.deleteAllInBatch();
        clientProfileRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
        professionRepository.deleteAllInBatch();
        entityManager.flush();
        entityManager.clear();
        log.info("Dados excluídos com sucesso.");

        // 2. Criar profissões
        ProfessionEntity eletricista = professionRepository.save(ProfessionEntity.builder().name("Eletricista").build());
        ProfessionEntity desenvolvedor = professionRepository.save(ProfessionEntity.builder().name("Desenvolvedor").build());
        ProfessionEntity encanador = professionRepository.save(ProfessionEntity.builder().name("Encanador").build());
        ProfessionEntity designer = professionRepository.save(ProfessionEntity.builder().name("Designer").build());
        ProfessionEntity personal = professionRepository.save(ProfessionEntity.builder().name("Personal Trainer").build());
        log.info("Profissões criadas: Eletricista, Desenvolvedor, Encanador, Designer, Personal Trainer");

        // 3. Criar usuários profissionais
        String senhaHash = passwordEncoder.encode("123456");

        UserEntity joao = userRepository.save(UserEntity.builder()
                .name("João Silva")
                .email("joao@email.com")
                .phone("11999990001")
                .role(UserRole.PROFESSIONAL)
                .passwordHash(senhaHash)
                .build());

        UserEntity maria = userRepository.save(UserEntity.builder()
                .name("Maria Oliveira")
                .email("maria@email.com")
                .phone("11999990002")
                .role(UserRole.PROFESSIONAL)
                .passwordHash(senhaHash)
                .build());

        UserEntity carlos = userRepository.save(UserEntity.builder()
                .name("Carlos Santos")
                .email("carlos@email.com")
                .phone("11999990003")
                .role(UserRole.PROFESSIONAL)
                .passwordHash(senhaHash)
                .build());

        // 4. Criar perfis profissionais
        professionalProfileRepository.save(ProfessionalProfileEntity.builder()
                .user(joao)
                .profession(eletricista)
                .bio("Eletricista com 10 anos de experiência em instalações residenciais e comerciais.")
                .hourlyRate(new BigDecimal("80.00"))
                .ratingAverage(4.8)
                .isAvailable(true)
                .build());

        professionalProfileRepository.save(ProfessionalProfileEntity.builder()
                .user(maria)
                .profession(desenvolvedor)
                .bio("Desenvolvedora fullstack especializada em Java e React.")
                .hourlyRate(new BigDecimal("150.00"))
                .ratingAverage(4.9)
                .isAvailable(true)
                .build());

        professionalProfileRepository.save(ProfessionalProfileEntity.builder()
                .user(carlos)
                .profession(encanador)
                .bio("Encanador profissional, atendo urgências 24h.")
                .hourlyRate(new BigDecimal("70.00"))
                .ratingAverage(4.5)
                .isAvailable(false)
                .build());

        log.info("Profissionais criados: João (Eletricista), Maria (Desenvolvedora), Carlos (Encanador)");

        // 5. Criar usuários clientes
        UserEntity ana = userRepository.save(UserEntity.builder()
                .name("Ana Costa")
                .email("ana@email.com")
                .phone("11999990004")
                .role(UserRole.CLIENT)
                .passwordHash(senhaHash)
                .build());

        UserEntity pedro = userRepository.save(UserEntity.builder()
                .name("Pedro Almeida")
                .email("pedro@email.com")
                .phone("11999990005")
                .role(UserRole.CLIENT)
                .passwordHash(senhaHash)
                .build());

        // 6. Criar perfis de clientes
        clientProfileRepository.save(ClientProfileEntity.builder()
                .user(ana)
                .taxId("123.456.789-00")
                .preferredPaymentMethod("PIX")
                .build());

        clientProfileRepository.save(ClientProfileEntity.builder()
                .user(pedro)
                .taxId("987.654.321-00")
                .preferredPaymentMethod("CARTAO_CREDITO")
                .build());

        log.info("Clientes criados: Ana, Pedro");

        // 7. Criar tipos de serviço
        ServiceTypeEntity instalacaoEletrica = serviceTypeRepository.save(ServiceTypeEntity.builder()
                .name("Instalação Elétrica")
                .description("Instalação de tomadas, disjuntores e fiação em geral")
                .owner(joao)
                .build());

        ServiceTypeEntity manutencaoEletrica = serviceTypeRepository.save(ServiceTypeEntity.builder()
                .name("Manutenção Elétrica")
                .description("Reparo e manutenção de sistemas elétricos")
                .owner(joao)
                .build());

        ServiceTypeEntity devWeb = serviceTypeRepository.save(ServiceTypeEntity.builder()
                .name("Desenvolvimento Web")
                .description("Criação de sites e aplicações web")
                .owner(maria)
                .build());

        ServiceTypeEntity devApi = serviceTypeRepository.save(ServiceTypeEntity.builder()
                .name("Desenvolvimento de API")
                .description("Criação de APIs REST e integração de sistemas")
                .owner(maria)
                .build());

        ServiceTypeEntity reparoHidraulico = serviceTypeRepository.save(ServiceTypeEntity.builder()
                .name("Reparo Hidráulico")
                .description("Conserto de vazamentos, troca de torneiras e válvulas")
                .owner(carlos)
                .build());

        log.info("Tipos de serviço criados: 5 serviços");

        // 8. Criar agendamentos
        appointmentRepository.save(AppointmentEntity.builder()
                .serviceType(instalacaoEletrica)
                .professional(joao)
                .client(ana)
                .valueInCents(16000)
                .scheduleDate(LocalDateTime.of(2026, 3, 15, 9, 0))
                .build());

        appointmentRepository.save(AppointmentEntity.builder()
                .serviceType(devWeb)
                .professional(maria)
                .client(pedro)
                .valueInCents(60000)
                .scheduleDate(LocalDateTime.of(2026, 3, 18, 14, 0))
                .build());

        appointmentRepository.save(AppointmentEntity.builder()
                .serviceType(manutencaoEletrica)
                .professional(joao)
                .client(pedro)
                .valueInCents(12000)
                .scheduleDate(LocalDateTime.of(2026, 3, 20, 10, 30))
                .build());

        appointmentRepository.save(AppointmentEntity.builder()
                .serviceType(devApi)
                .professional(maria)
                .client(ana)
                .valueInCents(45000)
                .scheduleDate(LocalDateTime.of(2026, 3, 22, 16, 0))
                .build());

        log.info("Agendamentos criados: 4 agendamentos");
        log.info("=== Seed concluído com sucesso! ===");
        log.info("Senha padrão de todos os usuários: 123456");
    }
}
