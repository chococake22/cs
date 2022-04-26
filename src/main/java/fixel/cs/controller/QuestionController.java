package fixel.cs.controller;

import fixel.cs.dto.request.QuesRegisterRequest;
import fixel.cs.dto.request.QuesUpdateRequest;
import fixel.cs.service.QuestionService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/question")
@RequiredArgsConstructor
@Slf4j
public class QuestionController {

    private final QuestionService questionService;

    @ApiOperation(value = "개별 요청사항 상세보기", notes = "개별 요청사항을 조회합니다.")
    @GetMapping("/{reqNo}")
    public ResponseEntity findRequestInfoDetail(@PathVariable Long reqNo) {
        return questionService.getQuestionInfoOne(reqNo);
    }

    @ApiOperation(value = "미해결된 요청사항 조회", notes = "미해결된 요청사항을 조회합니다.")
    @GetMapping("/notEnd")
    public ResponseEntity findNotEndQuestionAll(Pageable pageable) {
        return questionService.getNotEndQuestionList(pageable);
    }

    @ApiOperation(value = "해결된 요청사항 조회", notes = "해결된 요청사항을 조회합니다.")
    @GetMapping("/End")
    public ResponseEntity findEndQuestionAll(Pageable pageable) {
        return questionService.getEndQuestionList(pageable);
    }

    @ApiOperation(value = "요청사항 생성", notes = "요청사항을 생성합니다.")
    @PostMapping("/add")
    public ResponseEntity createQuestion(QuesRegisterRequest quesRegisterRequest, @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        return questionService.addQuestion(quesRegisterRequest, files);
    }

    @ApiOperation(value = "요청사항 수정", notes = "담당자가 요청사항을 수정합니다.")
    @PutMapping("/{reqNo}")
    public ResponseEntity updateQuestion(@PathVariable Long reqNo, @RequestBody QuesUpdateRequest quesUpdateRequest) {
        return questionService.updateQuestion(reqNo, quesUpdateRequest);
    }
}
