package fixel.cs.service;

import fixel.cs.entity.AttachedFile;
import fixel.cs.util.FileWriter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;


import java.io.IOException;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@Service
public class FileService {

    private final FileWriter fileWriter;

    public AttachedFile upload(MultipartFile sourceFile) throws IOException {

        String fileId = UUID.randomUUID().toString();
        String filePath = fileWriter.getFilePath(fileId, sourceFile);
        log.info("filePath: {}", filePath);
        fileWriter.writeFile(sourceFile, filePath);

        return AttachedFile.builder()
                .fileName(sourceFile.getName())
                .filePath(filePath)
                .build();

    }

}
