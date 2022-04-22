package fixel.cs.service;

import fixel.cs.dto.request.ReqReadRequest;
import fixel.cs.dto.request.ReqRegisterRequest;
import fixel.cs.dto.request.ReqUpdateRequest;
import fixel.cs.dto.user.UserLoginRequest;
import fixel.cs.entity.Request;
import fixel.cs.entity.User;
import fixel.cs.repository.RequestRepository;
import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final RequestRepository requestRepository;

    private static final Long userNo = 2L;
    private static final String userEmail = "test@test.com";
    private static final String password = "1234";

    // 개별 요청사항 상세보기
    @Transactional
    public ResponseEntity getRequestInfoOne(Long reqNo) {

        Request request = Optional.ofNullable(requestRepository.getById(reqNo))
                .orElseThrow(IllegalArgumentException::new);

        ReqRegisterRequest reqRegisterRequest = ReqRegisterRequest.builder()
                .title(request.getTitle())  // 제목
                .director(new User())   // 담당자
                .relatedUserNos(new ArrayList<>())  // 관련자
                .content(request.getContent())  // 내용
                .projectType(ProjectType.APL)   // 프로젝트 항목
                .requestType(RequestType.REQUEST_DATA)  // 요청 타입
                .statusCd(StatusCd.OPEN)
                .level(Level.EMERGENCY) // 중요도
                .regDt(request.getRegDt())
                .build();

        return ResponseEntity.ok().body(reqRegisterRequest);
    }

    // 요청사항 생성
    @Transactional
    public ResponseEntity addRequest(ReqRegisterRequest requestRegRequest) {

        Request request = Request.builder()
                .title(requestRegRequest.getTitle())
                .regUserNo(1L)
                .dirUserNo(2L)
                .relatedUserNos(new ArrayList<>())
                .content(requestRegRequest.getContent())
                .projectType(requestRegRequest.getProjectType())
                .statusCd(StatusCd.CHECK)
                .requestType(RequestType.REQUEST_DATA)
                .level(Level.EMERGENCY)
                .regDt(requestRegRequest.getRegDt())
                .build();

        requestRepository.save(request);

        return ResponseEntity.ok().body(request);
    }

    // 요청사항 수정
    @Transactional
    public ResponseEntity updateRequest(Long reqNo, ReqUpdateRequest reqUpdateRequest) {

        // 로그인한 사용자와 담당자가 같으면 요청 수정 가능
        // 토큰을 이용해서 로그인한 사용자

        Request request = Optional.ofNullable(requestRepository.getById(reqNo))
                .orElseThrow(IllegalArgumentException::new);

        log.info("reqUpdateRequest.getDir.getNo = {}", reqUpdateRequest.getDirector().getNo());

        try {
            // 해당 요청의 담당자의 번호가 로그인한 회원의 번호와 일치할 경우
            if (reqUpdateRequest.getDirector().getNo().equals(2L)) {

                request.update(reqUpdateRequest);

                return ResponseEntity.status(HttpStatus.OK).body(request);
            } else {
                throw new IllegalArgumentException("잘못된 접근입니다.");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }


        return new ResponseEntity("????", HttpStatus.BAD_REQUEST);
    }

    // 미해결 요청사항 조회
    // 상태코드가 CLOSE가 아닌 것은 다 가져온다.
//    @Transactional
//    public ResponseEntity<List<ReqReadRequest>> getNotEndRequestList(StatusCd statusCd) {
//
//
//        return ResponseEntity.ok().body();
//
//    }

    // 해결된 요청사항 조회
    // 상태코드가 CLOSE인 것 다 가져오기
    @Transactional
    public ResponseEntity<List<ReqReadRequest>> getEndRequestList() {

        List<Request> requestList = requestRepository.findAllByStatusCd(StatusCd.CLOSE);

        List<ReqReadRequest> reqReadRequestList
                = requestList.stream()
                .map(request -> new ReqReadRequest(
                        request.getTitle(),
                        new User(),
                        new ArrayList<>(),
                        request.getContent(),
                        new ArrayList<>(),
                        ProjectType.APL,
                        StatusCd.CHECK,
                        RequestType.REQUEST_DATA,
                        Level.EMERGENCY,
                        request.getRegDt()
                        )).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(reqReadRequestList);

    }

    // 미해결이면서 나에게 할당된 요청사항 조회
//    @Transactional
//    public ResponseEntity<List<ReqReadRequest>> getNotEndMyRequestList() {
//
//        // 로그인한 사용자와 담당자가 같아야 한다.
//        // 상태가 CLOSE가 아니어야 한다.
//        // SQL에서 CLOSE가 아닌 모든 것을 가져오는 것을 JPQL로 가져와야한다????
//
//
//
//
//        return ResponseEntity.ok();
//
//    }
}
