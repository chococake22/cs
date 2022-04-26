package fixel.cs.controller;

import fixel.cs.dto.request.ReqRegisterRequest;
import fixel.cs.dto.request.ReqUpdateRequest;
import fixel.cs.service.RequestService;

import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.Valid;
import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
@Slf4j
public class RequestController {

    private final RequestService requestService;

    @ApiOperation(value = "개별 요청사항 상세보기", notes = "개별 요청사항을 조회합니다.")
    @GetMapping("/{reqNo}")
    public ResponseEntity findRequestInfoDetail(@PathVariable Long reqNo) {
        return requestService.getRequestInfoOne(reqNo);
    }

    @ApiOperation(value = "미해결된 요청사항 조회", notes = "미해결된 요청사항을 조회합니다.")
    @GetMapping("/notEnd")
    public ResponseEntity findNotEndRequestAll(Pageable pageable) {
        return requestService.getNotEndRequestList(pageable);
    }

    @ApiOperation(value = "해결된 요청사항 조회", notes = "해결된 요청사항을 조회합니다.")
    @GetMapping("/End")
    public ResponseEntity findEndRequestAll(Pageable pageable) {
        return requestService.getEndRequestList(pageable);
    }

    // 나에게 할당된 미해결 요청사항 조회 추가

    @ApiOperation(value = "요청사항 생성", notes = "요청사항을 생성합니다.")
    @PostMapping("/register")
    public ResponseEntity saveRequest(ReqRegisterRequest request, @RequestPart(value = "file", required = false) List<MultipartFile> files) throws IOException {
        return requestService.addRequest(request, files);
    }

    @ApiOperation(value = "요청사항 수정", notes = "담당자가 요청사항을 수정합니다.")
    @PutMapping("/{reqNo}")
    public ResponseEntity updateRequest(@PathVariable Long reqNo, @RequestBody @Valid ReqUpdateRequest reqUpdateRequest) {
        return requestService.updateRequest(reqNo, reqUpdateRequest);
    }
}
