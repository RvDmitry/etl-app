package my.home.etlapp.service;

import lombok.RequiredArgsConstructor;
import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.mapper.BusinessMapper;
import my.home.etlapp.repository.BusinessRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BusinessServiceImpl implements BusinessService {

    private final BusinessRepository businessRepository;
    private final BusinessMapper businessMapper;

    @Transactional
    @Override
    public BusinessDto save(BusinessDto dto) {
        var entity = businessMapper.fromDtoToEntity(dto);
        businessRepository.findByParentId(dto.id())
                .ifPresent(businessEntity -> entity.setId(businessEntity.getId()));
        businessRepository.save(entity);
        return businessMapper.fromEntityToDto(entity);
    }

    @Transactional
    @Override
    public void delete(Long id) {
        businessRepository.deleteByParentId(id);
    }

}
