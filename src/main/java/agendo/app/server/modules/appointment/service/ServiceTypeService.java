package agendo.app.server.modules.appointment.service;

import java.util.List;

import org.springframework.stereotype.Service;

import agendo.app.server.modules.appointment.models.ServiceTypeEntity;
import agendo.app.server.modules.appointment.repository.ServiceTypeRepository;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeEntity create(ServiceTypeEntity serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public ServiceTypeEntity findById(Long id) {
        return serviceTypeRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("ServiceType not found: " + id));
    }

    public List<ServiceTypeEntity> findAll() {
        return serviceTypeRepository.findAll();
    }
}
