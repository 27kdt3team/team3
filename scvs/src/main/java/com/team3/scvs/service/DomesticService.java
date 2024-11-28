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

    /*
    @Transactional
    //국내 경제 뉴스 리스트 보여주기(entity 객체를 dto 객체에 옮겨 담기)
    public List<DomesticDto> findAll() {
        List<DomesticEntity> domesticEntityList = domesticRepository.findAll();

        //entity 객체를 dto 객체로 옮겨 담을 리스트 객체 선언
        List<DomesticDto> domesticDtoList = new ArrayList<>();

        for (DomesticEntity domesticEntity: domesticEntityList) {
            domesticDtoList.add(DomesticDto.toDomesticDto(domesticEntity));
        }

        return domesticDtoList;

    }
    */

    //DB에서 데이터를 페이징 처리해서 가져오기
    public Page<DomesticDto> getDomesticList(PageRequest pageRequest) {
        return domesticRepository.findAll(pageRequest).map(domestic ->
                new DomesticDto(domestic.getTitle(), domestic.getSource(), domestic.getPublishedAt(), domestic.getImageLink())
        );

    }

}
