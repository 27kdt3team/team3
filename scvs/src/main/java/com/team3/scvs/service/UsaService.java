package com.team3.scvs.service;

import com.team3.scvs.dto.UsaDto;
import com.team3.scvs.repository.UsaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsaService {
    private final UsaRepository usaRepository;

    public Page<UsaDto> getUsaList(PageRequest pageRequest) {
        return usaRepository.findAll(pageRequest).map(usa ->
                new UsaDto(usa.getTitle(), usa.getSource(), usa.getPublishedAt(), usa.getImageLink())
        );
    }

}
