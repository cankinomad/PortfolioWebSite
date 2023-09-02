package org.berka.mapper;

import javax.annotation.processing.Generated;
import org.berka.Repository.entity.Work;
import org.berka.dto.request.SaveRequestDto;
import org.springframework.stereotype.Component;

@Generated(
    value = "org.mapstruct.ap.MappingProcessor",
    date = "2023-09-02T15:22:31+0300",
    comments = "version: 1.5.5.Final, compiler: javac, environment: Java 19.0.2 (Oracle Corporation)"
)
@Component
public class IWorkMapperImpl implements IWorkMapper {

    @Override
    public Work toWork(SaveRequestDto dto) {
        if ( dto == null ) {
            return null;
        }

        Work.WorkBuilder<?, ?> work = Work.builder();

        work.title( dto.getTitle() );
        work.excerpt( dto.getExcerpt() );

        return work.build();
    }
}
