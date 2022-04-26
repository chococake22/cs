package fixel.cs.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import fixel.cs.dto.request.QuesReadRequest;
import fixel.cs.dto.request.QuesRegisterRequest;
import fixel.cs.dto.request.QuesUpdateRequest;
import fixel.cs.entity.AttachedFile;
import fixel.cs.entity.Question;

import fixel.cs.repository.FileRepository;
import fixel.cs.repository.QuestionRepository;

import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;
import fixel.cs.util.FileWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

import org.springframework.data.domain.Page;
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
public class QuestionService {

    private final QuestionRepository questionRepository;
    private final FileRepository fileRepository;
    private final FileWriter fileWriter;
    private final ObjectMapper objectMapper;

    // 로그인 이후 과정까지 생각해서 추가

    // 개별 요청사항 상세보기
    @Transactional
    public ResponseEntity getQuestionInfoOne(Long reqNo) {

        Question question = Optional.ofNullable(questionRepository.getById(reqNo))
                .orElseThrow(IllegalArgumentException::new);

        QuesReadRequest reqReadRequest = QuesReadRequest.builder()
                .title(question.getTitle())  // 제목
                .dirUserNo(question.getDirUserNo())   // 담당자
                .relatedUserNos(question.getRelatedUserNos())  // 관련자
                .content(question.getContent())  // 내용
                .projectType(ProjectType.APL)   // 프로젝트 항목
                .requestType(RequestType.REQUEST_DATA)  // 요청 타입
                .statusCd(StatusCd.OPEN)
                .level(Level.EMERGENCY) // 중요도
                .regDt(question.getRegDt())
                .build();

        return ResponseEntity.ok().body(reqReadRequest);
    }

    // 요청사항 생성(파일 같이 첨부)
    @Transactional
    public ResponseEntity addQuestion(QuesRegisterRequest quesRegisterRequest, List<MultipartFile> files) throws IOException {

        Question question = Question.builder()
                    .title(quesRegisterRequest.getTitle())
                    .regUserNo(quesRegisterRequest.getDirector())
                    .dirUserNo(quesRegisterRequest.getDirector())
                    .content(quesRegisterRequest.getContent())
                    .relatedUserNos(quesRegisterRequest.getRelatedUserNos())
                    .projectType(quesRegisterRequest.getProjectType())
                    .statusCd(quesRegisterRequest.getStatusCd())
                    .requestType(quesRegisterRequest.getRequestType())
                    .level(quesRegisterRequest.getLevel())
                    .regDt(LocalDateTime.now())
                    .build();

        questionRepository.save(question);

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
                        .question(question)
                        .build();

                // 각각의 파일들을 하나로 묶어서 전송하기

                fileRepository.save(attachedFile);
            }
        }

        // 담당자, 관련자에게 메일을 보내야 한다.

        return ResponseEntity.ok().body(question);
    }


    // 요청사항 수정
    @Transactional
    public ResponseEntity updateQuestion(Long reqNo, QuesUpdateRequest quesUpdateRequest) {

        // 로그인한 사용자와 담당자가 같으면 요청 수정 가능
        // 토큰을 이용해서 로그인한 사용자(추후 추가)

        Question question = Optional.ofNullable(questionRepository.getById(reqNo))
                .orElseThrow(IllegalArgumentException::new);

        log.info("reqUpdateRequest.getDir.getNo = {}", quesUpdateRequest.getDirUserNo());

        try {
            // 해당 요청의 담당자의 번호가 로그인한 회원의 번호와 일치할 경우
            if (quesUpdateRequest.getDirUserNo().equals(3L)) {

                question.update(quesUpdateRequest);

                return ResponseEntity.status(HttpStatus.OK).body(question);
            } else {
                throw new IllegalArgumentException("잘못된 접근입니다.");
            }
        } catch (Exception e) {
            log.info(e.getMessage());
        }

        // 변경 내역을 생성할 수 있는 로직 추가

        return new ResponseEntity("????", HttpStatus.BAD_REQUEST);
    }

    // 미해결 요청사항 조회
    // 상태코드가 CLOSE가 아닌 것은 다 가져온다.
    @Transactional
    public ResponseEntity<List<QuesReadRequest>> getNotEndQuestionList(Pageable pageable) {

        int page;

        if(pageable.getPageNumber() == 0) {
            page = 0;
        } else {
            page = pageable.getPageNumber() - 1;
        }

        pageable = (Pageable) PageRequest.of(page, 20, Sort.by(Sort.Direction.DESC, "regDt"));

        // Page 사용하기
        Page<Question> requestList = questionRepository.findAllByStatusCdIsNot(StatusCd.CLOSE, pageable);

        List<QuesReadRequest> reqReadRequestList
                = requestList.stream()
                .map(request -> new QuesReadRequest(
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

        // 리턴하기 위한 리턴 객체를 커스텀해서 보낼 수 있도록 수정 / ObjectMapper

        return ResponseEntity.status(HttpStatus.OK).body(reqReadRequestList);

    }

    // 해결된 요청사항 조회
    // 상태코드가 CLOSE인 것 다 가져오기
    @Transactional
    public ResponseEntity<Page<Question>> getEndQuestionList(Pageable pageable) {

        int page;

        if (pageable.getPageNumber() == 0) {
            page = 0;
        } else {
            page = pageable.getPageNumber() - 1;
        }

        pageable = (Pageable) PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "regDt"));

        Page<Question> questions = questionRepository.findAllByStatusCd(StatusCd.CLOSE, pageable);

//        Page<QuesReadRequest> quesReadRequestList
//                = questions.stream()
//                .map(question -> new QuesReadRequest(
//                        question.getTitle(),
//                        question.getDirUserNo(),
//                        new ArrayList<>(),
//                        question.getContent(),
//                        new ArrayList<>(),
//                        ProjectType.APL,
//                        StatusCd.CLOSE,
//                        RequestType.REQUEST_DATA,
//                        Level.EMERGENCY,
//                        question.getRegDt()
//                        )).collect(Collectors.toList());
//
//        return ResponseEntity.status(HttpStatus.OK).body(quesReadRequestList);
//    }

        return ResponseEntity.status(HttpStatus.OK).body(questions);
    }
}
