package com.team3.scvs.service;

import com.team3.scvs.dto.DomesticDto;
import com.team3.scvs.entity.DomesticEntity;
import com.team3.scvs.repository.DomesticRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class DomesticService {
    private final DomesticRepository domesticRepository;

    //뉴스 목록 조회
    public Page<DomesticDto> getDomesticList(Pageable pageable) {
        int page = pageable.getPageNumber() - 1; //실제 사용자가 요청한 페이지 값에서 하나 뺀 값 (page 인덱스는 0부터 시작)
        int pageLimit = 5; //한 페이지에 보여줄 기사 개수

        //최근 기사부터 내림차순 정렬
        Page<DomesticEntity> domesticEntities = domesticRepository.findAll(PageRequest.of(page, pageLimit, Sort.by(Sort.Direction.DESC, "korEconNewsId")));

        //map 메소드를 이용하여 엔티티를 dto 객체로 바꿔서 반환
        return domesticEntities.map(domestic -> new DomesticDto(
                domestic.getKorEconNewsId(),
                domestic.getTitle(),
                domestic.getSource(),
                domestic.getPublishedAt(),
                domestic.getImageLink()));

    }

    //뉴스 상세 페이지 조회
    public DomesticDto findById(Long korEconNewsId) {
        //주어진 korEconNewsId로 엔티티 조회
        Optional<DomesticEntity> optionalDomesticEntity = domesticRepository.findById(korEconNewsId);

        if (optionalDomesticEntity.isPresent()) {//데이터가 존재하면
            //엔티티 객체를 DomesticEntity 타입 변수에 저장
            DomesticEntity domesticEntity = optionalDomesticEntity.get();

            //엔티티를 dto 객체로 바꿔서 반환
            return DomesticDto.toDomesticDto(domesticEntity);

        } else {//데이터가 존재하지 않으면
            return null;

        }

    }
}
