package com.sparta.memo.controller;

import com.sparta.memo.dto.MemoRequestDto;
import com.sparta.memo.dto.MemoResponseDto;
import com.sparta.memo.entity.Memo;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api")
public class MemoController {

    private final Map<Long, Memo> memoList = new HashMap<>();   // db와 연결하기 위한 데이터 컬렉션 생성.

    @PostMapping("/memos")
    public MemoResponseDto createMemo(@RequestBody MemoRequestDto requestDto) {
        Memo memo = new Memo(requestDto);

        Long maxId = memoList.size() > 0 ? Collections.max(memoList.keySet()) + 1 : 1;
        memo.setId(maxId);

        //db에 저장.
        memoList.put(memo.getId(), memo);

        // 엔티티 -> responseDto 변환.
        MemoResponseDto memoResponseDto = new MemoResponseDto(memo);
        return memoResponseDto;
    }

    // 메모장 읽기
    @GetMapping("/memos")
    public List<MemoResponseDto> getMemos() {
        List<MemoResponseDto> responseList = memoList.values().stream()
                .map(MemoResponseDto::new).toList();

        return responseList;

    }

    // Update구현
    @PutMapping("/memos/{id}")
    public Long updateMemo(@PathVariable Long id, @RequestBody MemoRequestDto requestDto){
        // db에 메모가 있는지 확인.
        if(memoList.containsKey(id)){
            Memo memo = memoList.get(id);

            // 메모를 수정.
            memo.update(requestDto);
            return memo.getId();
        }
        else {
            throw new IllegalArgumentException("선택한 메모는 존재 x.");
        }
    }

    // Delete 구현
    @DeleteMapping("/memos/{id}")
    public Long deleteMemo(@PathVariable Long id){
        // db에 메모가 존재여부 판단.
        if(memoList.containsKey(id)){
            memoList.remove(id);
            return id;
        }
        else {
            throw new IllegalArgumentException("메모는 존재하지 x");
        }
    }
}


