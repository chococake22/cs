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

    @ApiOperation(value = "요청사항 생성", notes = "요청사항을 생성합니다.")
    @PostMapping("/add")
    public ResponseEntity createRequest(ReqRegisterRequest reqRegisterRequest, @RequestPart(required = false) List<MultipartFile> files) throws IOException {
        return requestService.addRequest(reqRegisterRequest, files);
    }

    @ApiOperation(value = "요청사항 수정", notes = "담당자가 요청사항을 수정합니다.")
    @PutMapping("/{reqNo}")
    public ResponseEntity updateRequest(@PathVariable Long reqNo, ReqUpdateRequest reqUpdateRequest) {
        return requestService.updateRequest(reqNo, reqUpdateRequest);
    }

//    @ApiOperation(value = "담당자 변경", notes = "담당자를 변경합니다.")
//    @PutMapping("/{reqNo}/director")
//    public ResponseEntity updateDirector(@PathVariable Long reqNo, @RequestBody ReqUpdateRequest reqUpdateRequest) {
//        return requestService.updateRequest(reqNo, reqUpdateRequest);
//    }

//    @ApiOperation(value = "관련자 변경", notes = "관련자를 변경합니다.")
//    @PutMapping("/{reqNo}/reluser")
//    public ResponseEntity updateRelUser(@PathVariable Long reqNo, @RequestBody ReqUpdateRequest reqUpdateRequest) {
//        return requestService.updateRequest(reqNo, reqUpdateRequest);
//    }

}
