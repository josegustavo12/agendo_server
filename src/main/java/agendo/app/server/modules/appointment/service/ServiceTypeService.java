package agendo.app.server.modules.appointment.service;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import agendo.app.server.modules.appointment.models.ServiceTypeEntity;
import agendo.app.server.modules.appointment.repository.ServiceTypeRepository;
import agendo.app.server.modules.user.models.UserEntity;
import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class ServiceTypeService {

    private final ServiceTypeRepository serviceTypeRepository;

    public ServiceTypeEntity create(ServiceTypeEntity serviceType) {
        return serviceTypeRepository.save(serviceType);
    }

    public ServiceTypeEntity findByIdAndOwner(Long id, UserEntity owner) {
        return serviceTypeRepository.findByIdAndOwner(id, owner)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "ServiceType not found: " + id));
    }

    public List<ServiceTypeEntity> findByOwner(UserEntity owner) {
        return serviceTypeRepository.findByOwner(owner);
    }
}
