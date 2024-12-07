package com.team3.scvs.util;

import com.team3.scvs.dto.*;
import com.team3.scvs.entity.*;

public class ConvertUtil {
    // Entity -> DTO 변환 메서드
    public CommunityStockInfoDTO convertToDTO(CommunityStockInfoEntity entity){
        return new CommunityStockInfoDTO(
                entity.getTickerId(),
                entity.getSymbol(),
                entity.getCompany(),
                entity.getMarket(),
                entity.getCurrentprice()
        );
    }

    public CommunityCommentDTO convertToDTO(CommunityCommentEntity entity) {
        return new CommunityCommentDTO(
                entity.getCommunityCommentId(),
                entity.getCommunity().getCommunityId(),
                entity.getUser().getUserId(),
                entity.getComment(),
                entity.getPublishedAt(),
                entity.getUpdatedAt()
        );
    }

    public CommunityVoteDTO convertToDTO(CommunityVoteEntity entity) {
        return new CommunityVoteDTO(
                entity.getCommunityVoteId(),
                entity.getCommunity().getCommunityId(),
                entity.getPositiveVotes(),
                entity.getNegativeVotes()
        );
    }

    public CommunityDTO convertToDTO(CommunityEntity entity) {
        return new CommunityDTO(
                entity.getCommunityId(),
                entity.getTickerId()
        );
    }

    public StocksDTO convertToDTO(StocksEntity entity) {
        return new StocksDTO(
                entity.getStockId(),
                entity.getTickerId(),
                entity.getMarket(),
                entity.getCurrentPrice(),
                entity.getClose(),
                entity.getOpen(),
                entity.getVolume(),
                entity.getFiftytwoWeekLow(),
                entity.getFiftytwoWeekHigh(),
                entity.getDayLow(),
                entity.getDayHigh(),
                entity.getReturnOnAssets(),
                entity.getReturnOnEquity(),
                entity.getEnterpriseValue(),
                entity.getEnterpriseToEBITDA(),
                entity.getPriceToBook(),
                entity.getPriceToSales(),
                entity.getEarningsPerShare(),
                entity.getCurrentRatio(),
                entity.getDebtToEquity()
        );
    }

    public StockNewsTitleDTO convertToDTO(StockNewsTitleEntity entity) {
        return new StockNewsTitleDTO(
                entity.getId(),
                entity.getTitle(),
                entity.getTickerId(),
                entity.getSentiment(),
                entity.getPublishedAt(),
                entity.getMarket()
        );
    }
    public CommunityCommentViewDTO convertToDTO(CommunityCommentViewEntity entity) {
        return new CommunityCommentViewDTO(
                entity.getId(),
                entity.getUserId(),
                entity.getCommunityId(),
                entity.getNickname(),
                entity.getComment(),
                entity.getPublishedAt(),
                entity.getUpdatedAt(),
                entity.getTimeAgo()
        );
    }
  
    public StockNewsDTO convertToDTO(StockNewsEntity entity) {
        return StockNewsDTO.builder()
                .stockNewsId(entity.getStockNewsId())
                .title(entity.getTitle())
                .source(entity.getSource())
                .imageLink(entity.getImageLink())
                .content(entity.getContent())
                .sentiment(entity.getSentiment())
                .publishedAt(entity.getPublishedAt())
                .link(entity.getLink())
                .tickerId(entity.getTickerId())
                .market(entity.getMarket())
                .build();
    }
}
