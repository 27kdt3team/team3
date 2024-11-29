package com.team3.scvs.service;

import com.team3.scvs.entity.PSA;
import com.team3.scvs.repository.PSARepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class PSAService {

    @Autowired
    private PSARepository psaRepository;

    // 모든 공지사항 조회 (최신순 정렬)
    public List<PSA> findAllPSAs() {
        return psaRepository.findAllByOrderByPublishedAtDesc();
    }

    // 특정 공지사항 ID로 조회
    public PSA findPSAById(Long id) {
        return psaRepository.findById(id).orElse(null);
    }

    // 제목으로 공지사항 검색
    public List<PSA> searchPSAsByTitle(String keyword) {
        return psaRepository.findByTitleContaining(keyword);
    }

    // 특정 기간 동안 작성된 공지사항 조회
    public List<PSA> findPSAsWithinDateRange(LocalDateTime startDate, LocalDateTime endDate) {
        return psaRepository.findByPublishedAtBetween(startDate, endDate);
    }

    // 새로운 공지사항 생성 또는 업데이트
    public PSA savePSA(PSA psa) {
        return psaRepository.save(psa);
    }

    // 특정 공지사항 삭제
    public void deletePSAById(Long id) {
        psaRepository.deleteById(id);
    }

    // 가장 최근 공지사항 조회
    public PSA findLatestPSA() {
        return psaRepository.findTopByOrderByPublishedAtDesc();
    }

    // 페이지네이션 지원 공지사항 목록 조회
    public Page<PSA> findAllPSAsPaginated(int page, int size) {
        Pageable pageable = PageRequest.of(page, size);
        return psaRepository.findAllByOrderByPublishedAtDesc(pageable);
    }

    // 페이징을 지원하는 검색 기능
    public Page<PSA> searchPSAsByTitlePaginated(String query, int page, int size) {
        Pageable pageable = PageRequest.of(page, size, Sort.by("publishedAt").descending());
        return psaRepository.findByTitleContaining(query, pageable);
    }

    // 이전 공지
    public PSA findPreviousPSA(Long id) {
        return psaRepository.findFirstByIdGreaterThanOrderByIdAsc(id);
    }

    // 다음 공지
    public PSA findNextPSA(Long id) {
        return psaRepository.findFirstByIdLessThanOrderByIdDesc(id);
    }


}
