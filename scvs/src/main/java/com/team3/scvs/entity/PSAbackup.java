package com.team3.scvs.entity;

public class PSAbackup {
    private int id;             // 공지사항 번호
    private String title;       // 공지사항 제목
    private String publishedAt; // 공지사항 작성일

    // 기본 생성자
    public PSAbackup() {}

    // 모든 필드를 초기화하는 생성자
    public PSAbackup(int id, String title, String publishedAt) {
        this.id = id;
        this.title = title;
        this.publishedAt = publishedAt;
    }

    // Getter 메서드
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getPublishedAt() {
        return publishedAt;
    }

    // toString 메서드 (디버깅용)
    @Override
    public String toString() {
        return "PSA{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", publishedAt='" + publishedAt + '\'' +
                '}';
    }
}
