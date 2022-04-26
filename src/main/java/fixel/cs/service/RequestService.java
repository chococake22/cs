package fixel.cs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fixel.cs.dto.request.ReqReadRequest;
import fixel.cs.dto.request.ReqRegisterRequest;
import fixel.cs.dto.request.ReqUpdateRequest;
import fixel.cs.entity.AttachedFile;
import fixel.cs.entity.Request;
import fixel.cs.entity.User;
import fixel.cs.repository.FileRepository;
import fixel.cs.repository.RequestRepository;
import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;
import fixel.cs.util.FileWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@Slf4j
public class RequestService {

    private final RequestRepository requestRepository;
    private final FileRepository fileRepository;
    private final FileWriter fileWriter;
    private final ObjectMapper objectMapper;

    // 임시 계정
    private static final Long userNo = 2L;
    private static final String userEmail = "test@test.com";
    private static final String password = "1234";

    // 개별 요청사항 상세보기
    @Transactional
    public ResponseEntity getRequestInfoOne(Long reqNo) {

        Request request = Optional.ofNullable(requestRepository.getById(reqNo))
                .orElseThrow(IllegalArgumentException::new);

        ReqReadRequest reqReadRequest = ReqReadRequest.builder()
                .title(request.getTitle())  // 제목
                .dirUserNo(request.getDirUserNo())   // 담당자
                .relatedUserNos(request.getRelatedUserNos())  // 관련자
                .content(request.getContent())  // 내용
                .projectType(ProjectType.APL)   // 프로젝트 항목
                .requestType(RequestType.REQUEST_DATA)  // 요청 타입
                .statusCd(StatusCd.OPEN)
                .level(Level.EMERGENCY) // 중요도
                .regDt(request.getRegDt())
                .build();

        return ResponseEntity.ok().body(reqReadRequest);
    }

    // 요청사항 생성(파일 같이 첨부)
    @Transactional
    public ResponseEntity addRequest(ReqRegisterRequest reqRegRequest, List<MultipartFile> files) throws IOException {

        Request request = Request.builder()
                    .title(reqRegRequest.getTitle())
                    .regUserNo(reqRegRequest.getRegUserNo())
                    .dirUserNo(reqRegRequest.getDirUserNo())
                    .content(reqRegRequest.getContent())
                    .relatedUserNos(reqRegRequest.getRelatedUserNos())
                    .projectType(reqRegRequest.getProjectType())
                    .statusCd(reqRegRequest.getStatusCd())
                    .requestType(reqRegRequest.getRequestType())
                    .level(reqRegRequest.getLevel())
                    .regDt(LocalDateTime.now())
                    .build();

        requestRepository.save(request);

        if (files != null) {

            for (MultipartFile file : files) {

                String contentType = file.getContentType();

                String originalFileExtension = "";

                if (contentType.contains("image/jpeg")) {
                    originalFileExtension = ".jpeg";
                } else if (contentType.contains("image/png")) {
                    originalFileExtension = ".png";
                } else if (contentType.contains("image/gif")) {
                    originalFileExtension = ".gif";
                } else {
                    log.info("file format : " + contentType);
                    throw new IOException("확장자 변경 필요");
                }

                // 파일 고유 id
                String fileId = UUID.randomUUID().toString();

                // 파일 저장 경로 생성
                String filePath = fileWriter.getFilePath(fileId, file);
                log.info("filePath: {}", filePath);

                // 해당 파일을 지정된 경로에 저장
                fileWriter.writeFile(file, filePath);

                AttachedFile attachedFile = AttachedFile.builder()
                        .fileName(file.getOriginalFilename())
                        .fileId(fileId)
                        .filePath(filePath)
                        .regDt(LocalDateTime.now())
                        .request(request)
                        .build();

                fileRepository.save(attachedFile);
            }
        }

        return ResponseEntity.ok().body(request);
    }


    // 요청사항 수정
    @Transactional
    public ResponseEntity updateRequest(Long reqNo, ReqUpdateRequest reqUpdateRequest) {

        // 로그인한 사용자와 담당자가 같으면 요청 수정 가능
        // 토큰을 이용해서 로그인한 사용자(추후 추가)

        Request request = Optional.ofNullable(requestRepository.getById(reqNo))
                .orElseThrow(IllegalArgumentException::new);

        log.info("reqUpdateRequest.getDir.getNo = {}", reqUpdateRequest.getDirUserNo());

        try {
            // 해당 요청의 담당자의 번호가 로그인한 회원의 번호와 일치할 경우
            if (reqUpdateRequest.getDirUserNo().equals(3L)) {

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
    @Transactional
    public ResponseEntity<List<ReqReadRequest>> getNotEndRequestList(Pageable pageable) {

        int page;

        if(pageable.getPageNumber() == 0) {
            page = 0;
        } else {
            page = pageable.getPageNumber() - 1;
        }

        pageable = (Pageable) PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "regDt"));

        List<Request> requestList = requestRepository.findAllByStatusCdIsNot(StatusCd.CLOSE, pageable);

        List<ReqReadRequest> reqReadRequestList
                = requestList.stream()
                .map(request -> new ReqReadRequest(
                        request.getTitle(),
                        request.getDirUserNo(),
                        new ArrayList<>(),
                        request.getContent(),
                        new ArrayList<>(),
                        ProjectType.APL,
                        request.getStatusCd(),
                        RequestType.REQUEST_DATA,
                        Level.EMERGENCY,
                        request.getRegDt()
                )).collect(Collectors.toList());

        return ResponseEntity.status(HttpStatus.OK).body(reqReadRequestList);

    }

    // 해결된 요청사항 조회
    // 상태코드가 CLOSE인 것 다 가져오기
    @Transactional
    public ResponseEntity<List<ReqReadRequest>> getEndRequestList(Pageable pageable) {

        int page;

        if(pageable.getPageNumber() == 0) {
            page = 0;
        } else {
            page = pageable.getPageNumber() - 1;
        }

        pageable = (Pageable) PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "regDt"));

        List<Request> requestList = requestRepository.findAllByStatusCd(StatusCd.CLOSE, pageable);

        List<ReqReadRequest> reqReadRequestList
                = requestList.stream()
                .map(request -> new ReqReadRequest(
                        request.getTitle(),
                        request.getDirUserNo(),
                        new ArrayList<>(),
                        request.getContent(),
                        new ArrayList<>(),
                        ProjectType.APL,
                        StatusCd.CLOSE,
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
