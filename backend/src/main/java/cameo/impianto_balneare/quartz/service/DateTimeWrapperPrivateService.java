package cameo.impianto_balneare.quartz.service;

import cameo.impianto_balneare.repository.DateTimeWrapperRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DateTimeWrapperPrivateService {
    private final DateTimeWrapperRepository dateTimeWrapperRepository;

    @Autowired
    public DateTimeWrapperPrivateService(DateTimeWrapperRepository dateTimeWrapperRepository) {
        this.dateTimeWrapperRepository = dateTimeWrapperRepository;
    }

    public void deleteNewsletterDateTime(UUID id) {
        dateTimeWrapperRepository.deleteById(id);
    }
}
