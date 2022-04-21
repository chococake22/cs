package fixel.cs.controller;

import fixel.cs.dto.user.UserLoginRequest;
import fixel.cs.dto.request.ReqRegisterRequest;
import fixel.cs.dto.request.ReqUpdateRequest;
import fixel.cs.service.RequestService;
import fixel.cs.type.StatusCd;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @ApiOperation(value = "개별 요청사항 상세보기", notes = "개별 요청사항을 조회합니다.")
    @GetMapping("/{reqNo}")
    public ResponseEntity findRequestInfoDetail(@PathVariable Long reqNo) {
        return requestService.getRequestInfoOne(reqNo);
    }

//    @ApiOperation(value = "미해결 요청사항 조회", notes = "미해결된 요청사항을 조회합니다.")
//    @GetMapping("/notEnd")
//    public ResponseEntity findNotEndRequestAll(StatusCd statusCd) {
//        return requestService.getNotEndRequestList(statusCd);
//    }

    @ApiOperation(value = "해결 요청사항 조회", notes = "해결된 요청사항을 조회합니다.")
    @GetMapping("/End")
    public ResponseEntity findEndRequestAll() {
        return requestService.getEndRequestList();
    }

    @ApiOperation(value = "요청사항 생성", notes = "요청사항을 생성합니다.")
    @PostMapping
    public ResponseEntity createRequest(@RequestBody ReqRegisterRequest reqRegisterRequest) {
        return requestService.addRequest(reqRegisterRequest);
    }

//    @ApiOperation(value = "요청사항 수정", notes = "담당자가 요청사항을 수정합니다.")
//    @PutMapping("/{reqNo}")
//    public ResponseEntity updateRequest(@PathVariable Long reqNo, @RequestBody ReqUpdateRequest reqUpdateRequest) {
//        return requestService.updateRequest(reqNo, reqUpdateRequest);
//    }

}
