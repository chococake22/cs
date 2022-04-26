package fixel.cs.util;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FilenameUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileWriter {

    private final PhotoAppProperties photoAppProperties;

    // 파일 저장
    public long writeFile(MultipartFile multipartFile, String filePath) throws IOException {

        File file = new File(filePath);
        FileCopyUtils.copy(multipartFile.getInputStream(), new FileOutputStream(file));

        try {
            // 파일을 MultipartFile을 이용해서 전송한다.
            multipartFile.transferTo(file);
        }  catch (IOException ioe) {
            throw new RuntimeException("ioe");
        }

        return multipartFile.getSize();
    }

    // 기본 경로에서 경로 얻기
    public String getFilePath(String fileId, MultipartFile sourceFile) {
        return photoAppProperties.getDefaultPath() +  "/" + fileId + "_" + sourceFile.getOriginalFilename();
    }

    private static String getMimeType(String filePath) {
        return FilenameUtils.getExtension(filePath);
    }

    public static String dateStr() {
        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyyMMdd");

        return now.format(dateTimeFormatter);
    }
}
