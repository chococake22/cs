package fixel.cs.dto.file;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Data;

@Data
public class FileUploadResponse {

    private String fileName;

    @JsonIgnore
    private String downloadUri;

    @JsonIgnore
    private long size;

}
