package fixel.cs.controller;

import fixel.cs.entity.AttachedFile;
import fixel.cs.service.FileService;
import fixel.cs.util.UploadResult;
import fixel.cs.service.FileService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RequiredArgsConstructor
@RestController
@CrossOrigin
public class FileController {

//    private final FileService uploadService;
//
//    @ApiOperation(value = "파일 업로드", notes = "파일을 업로드합니다.")
//    @PostMapping("/api/upload/{reqNo}")
//    public UploadResult transfer(@RequestParam("reqNo") Long reqNo, @RequestPart("AttachedFile")MultipartFile multipartFile) {
//
//        AttachedFile imageFile = uploadService.upload(multipartFile);
//
//        return UploadResult.builder()
//                .fileName(imageFile.getFileName())
//                .fileId(imageFile.getFileId())
//                .fileSize(imageFile.getFileSize())
//                .build();
//
//    }

}
