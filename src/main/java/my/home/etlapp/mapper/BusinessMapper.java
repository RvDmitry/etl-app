package my.home.etlapp.mapper;

import my.home.etlapp.dto.BusinessDto;
import my.home.etlapp.entity.BusinessEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface BusinessMapper {

    BusinessDto fromEntityToDto(BusinessEntity entity);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "parentId", source = "id")
    BusinessEntity fromDtoToEntity(BusinessDto dto);
}
