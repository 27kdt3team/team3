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
                new UsaDto(usa.getUsaEconNewsId(), usa.getTitle(), usa.getSource(), usa.getPublishedAt(), usa.getImageLink())
        );

    }

    /*
    //DB에서 데이터를 페이징 처리해서 가져오기
    public Page<DomesticDto> getDomesticList(Pageable pageable) {
        int page = pageable.getPageNumber() - 1;
        int pageLimit = 10; //한 페이지에 기사 10개씩 보여줌

        //page 위치에 있는 값은 0부터 시작 (즉, 실제 사용자가 요청한 페이지 값에서 하나 뺀 값을 가져옴)
        Page<DomesticEntity> domesticEntities = domesticRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "korEconNewsId")));

        //map 메소드를 이용하여 엔티티를 dto 객체로 바꿔줌
        Page<DomesticDto> domesticDtos = domesticEntities.map(domestic -> new DomesticDto(domestic.getKorEconNewsId(), domestic.getTitle(), domestic.getSource(), domestic.getPublishedAt(), domestic.getImageLink()));

        return domesticDtos;

    }
    */

}
