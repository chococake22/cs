package fixel.cs.controller;

import fixel.cs.dto.ReqRegisterRequest;
import fixel.cs.service.RequestService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/request")
@RequiredArgsConstructor
public class RequestController {

    private final RequestService requestService;

    @ApiOperation(value = "개별 요청사항 조회", notes = "개별 요청사항을 조회합니다.")
    @GetMapping("/{reqno}")
    public ResponseEntity findEnquiryInfoDetail(@PathVariable Long reqno) {
        return requestService.getRequestInfoOne(reqno);
    }

    @ApiOperation(value = "요청사항 생성", notes = "요청사항을 생성합니다.")
    @PostMapping
    public ResponseEntity createEnquiry(@RequestBody ReqRegisterRequest requestRegRequest) {
        return requestService.addRequest(requestRegRequest);
    }
}
