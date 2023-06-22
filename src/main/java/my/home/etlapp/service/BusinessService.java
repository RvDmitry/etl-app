package my.home.etlapp.service;

import my.home.etlapp.dto.BusinessDto;

public interface BusinessService {

    BusinessDto save(BusinessDto dto);

    void delete(Long id);

}
