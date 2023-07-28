package com.sparta.memo.dto;

import com.sparta.memo.entity.Memo;
import lombok.Getter;

@Getter
public class MemoResponseDto {
    private Long id;            // 비밀번호
    private String title;       // 제목
    private String username;    // 작성자명
    private String contents;    // 작성 내용

    public MemoResponseDto(Memo memo) {
    }
}
