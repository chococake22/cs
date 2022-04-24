package fixel.cs.dto.file;

import lombok.Data;

@Data
public class FileUploadResponse {

    private String fileName;
    private String downloadUri;
    private long size;

}
