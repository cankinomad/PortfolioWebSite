package org.berka.service;

import org.berka.Repository.IWorkRepository;
import org.berka.Repository.entity.Work;
import org.berka.dto.request.SaveRequestDto;
import org.berka.dto.request.UpdateRequestDto;
import org.berka.exception.ErrorType;
import org.berka.exception.WorkManagerException;
import org.berka.mapper.IWorkMapper;
import org.berka.utility.JwtTokenManager;
import org.berka.utility.ServiceManager;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class WorkService extends ServiceManager<Work,Long> {

    private final IWorkRepository repository;
    private final JwtTokenManager jwtTokenManager;

    public WorkService(IWorkRepository repository, JwtTokenManager jwtTokenManager) {
        super(repository);
        this.repository = repository;
        this.jwtTokenManager = jwtTokenManager;
    }

    public String saveWork(SaveRequestDto dto) {
        Work work = IWorkMapper.INSTANCE.toWork(dto);

        boolean existsByTitle = repository.existsByTitle(work.getTitle());
        if (existsByTitle) {
            throw new WorkManagerException(ErrorType.WORK_ALREADY_EXIST);
        }

        try {
            save(work);
        } catch (Exception e) {
            throw new WorkManagerException(ErrorType.WORK_NOT_CREATED);
        }

        Optional<String> token = jwtTokenManager.createToken(work.getId());
        if (token.isEmpty()) {
            throw new WorkManagerException(ErrorType.TOKEN_NOT_CREATED);
        }

        return work.getTitle() + " basariyla olusturulmustur.\nBu is uzerinde yapilacak degisiklikler icin gerekli token: "+token.get();
    }

    public String updateWork(UpdateRequestDto dto) {
        Optional<Long> OptionalId = jwtTokenManager.getIdFromToken(dto.getToken());

        Optional<Work> optionalWork = findById(OptionalId.get());
        if (optionalWork.isEmpty()) {
            throw new WorkManagerException(ErrorType.WORK_NOT_FOUND);
        }

        boolean existsByTitle = repository.existsByTitle(dto.getTitle());


        if (!optionalWork.get().getTitle().equals(dto.getTitle()) && existsByTitle) {
            throw new WorkManagerException(ErrorType.WORK_ALREADY_EXIST);
        }

        optionalWork.get().setCoverImage(dto.getCoverImage());
        optionalWork.get().setExcerpt(dto.getExcerpt());
        optionalWork.get().setTitle(dto.getTitle());
        optionalWork.get().setTags(dto.getTags());
        optionalWork.get().setDescription(dto.getDescription());
        optionalWork.get().setFeatured(dto.isFeatured());

        update(optionalWork.get());

        return optionalWork.get().getTitle() + " Basariyla guncellenmistir";
    }

    public Boolean deleteWork(String token) {
        Optional<Long> optionalId = jwtTokenManager.getIdFromToken(token);

        if (optionalId.isEmpty()) {
            throw new WorkManagerException(ErrorType.INVALID_TOKEN);
        }

        Optional<Work> optionalWork = findById(optionalId.get());
        if (optionalWork.isEmpty()) {
            throw new WorkManagerException(ErrorType.WORK_NOT_FOUND);
        }

        deleteById(optionalWork.get().getId());

        return true;
    }

    public Work findWorkById(Long id) {
        Optional<Work> optionalWork = findById(id);
        if (optionalWork.isEmpty()) {
            throw new WorkManagerException(ErrorType.WORK_NOT_FOUND);
        }
        return optionalWork.get();
    }
}
