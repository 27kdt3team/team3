package com.team3.scvs.service;

import com.team3.scvs.dto.UsaDto;
import com.team3.scvs.entity.UsaEntity;
import com.team3.scvs.repository.UsaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UsaService {
    private final UsaRepository usaRepository;

    public Page<UsaDto> getUsaList(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //실제 사용자가 요청한 페이지 값에서 하나 뺀 값 (page 위치에 있는 값은 0부터 시작)
        int pageLimit = 5; //한 페이지에 기사 5개씩 보여줌

        Page<UsaEntity> usaEntities = usaRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "usaEconNewsId")));

        //map 메소드를 이용하여 엔티티를 dto 객체로 바꿔줌
        return usaEntities.map(usa -> new UsaDto(
                usa.getUsaEconNewsId(),
                usa.getTitle(),
                usa.getSource(),
                usa.getPublishedAt(),
                usa.getImageLink()));

    }

}
