package org.berka.mapper;

import org.berka.Repository.entity.Work;
import org.berka.dto.request.SaveRequestDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring",unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface IWorkMapper {

    IWorkMapper INSTANCE = Mappers.getMapper(IWorkMapper.class);


    Work toWork(final SaveRequestDto dto);
}
