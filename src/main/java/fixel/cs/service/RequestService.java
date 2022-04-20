package fixel.cs.service;

import fixel.cs.dto.ReqRegisterRequest;
import fixel.cs.entity.Request;
import fixel.cs.entity.UpdateRecord;
import fixel.cs.entity.User;
import fixel.cs.repository.RequestRepository;
import fixel.cs.type.Level;
import fixel.cs.type.ProjectType;
import fixel.cs.type.RequestType;
import fixel.cs.type.StatusCd;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class RequestService {

    private final RequestRepository requestRepository;

    public ResponseEntity getRequestInfoOne(Long enqSeq) {

        Request request = Optional.ofNullable(requestRepository.getById(enqSeq))
                .orElseThrow(IllegalArgumentException::new);

        ReqRegisterRequest reqRegisterRequest = ReqRegisterRequest.builder()
                .title(request.getTitle())
                .director(new User())
                .relatedUserNos(new ArrayList<>())
                .content(request.getContent())
                .fileList(new ArrayList<>())
                .updateRecord(new UpdateRecord())
                .checked(true)
                .projectType(ProjectType.APL)
                .requestType(RequestType.REQUEST_DATA)
                .level(Level.EMERGENCY)
                .build();


        return ResponseEntity.ok().body(reqRegisterRequest);
    }

    @Transactional
    public ResponseEntity addRequest(ReqRegisterRequest requestRegRequest) {
        Request request = Request.builder()
                .title(requestRegRequest.getTitle())
                .regUserNo(1L)
                .dirUserNo(2L)
                .relatedUserNos(new ArrayList<>())
                .content(requestRegRequest.getContent())
                .projectType(requestRegRequest.getProjectType())
                .statusCd(StatusCd.NORMAL)
                .requestType(RequestType.REQUEST_DATA)
                .level(Level.EMERGENCY)
                .build();

        requestRepository.save(request);

        return ResponseEntity.ok().body(request);
    }

}
