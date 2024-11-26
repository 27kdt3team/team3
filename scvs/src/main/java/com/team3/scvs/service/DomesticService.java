package com.team3.scvs.service;

import com.team3.scvs.dto.DomesticDto;
import com.team3.scvs.repository.DomesticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DomesticService {
    private final DomesticRepository domesticRepository;

    public Page<DomesticDto> getDomesticList(PageRequest pageRequest) {
        return domesticRepository.findAll(pageRequest).map(domestic ->
                new DomesticDto(domestic.getTitle(), domestic.getSource(), domestic.getPublishedAt(), domestic.getImageLink())
        );
    }

}
