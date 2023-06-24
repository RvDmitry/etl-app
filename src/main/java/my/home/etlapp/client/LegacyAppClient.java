package my.home.etlapp.client;

import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.entity.BusinessType;
import org.springframework.data.domain.Page;

import java.time.LocalDate;
import java.util.Set;

public interface LegacyAppClient {

    BusinessDto getValue(long id);

    Page<BusinessDto> pageByType(Set<BusinessType> types, int page, int size);

    Page<BusinessDto> pageByCreationDate(LocalDate dateFrom, LocalDate dateTo, int page, int size);
}
