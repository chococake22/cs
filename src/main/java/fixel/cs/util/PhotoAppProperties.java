package fixel.cs.util;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Slf4j
@Configuration
@Data
public class PhotoAppProperties {

    // properties를 이용해서 기본 경로를 지정한다.
    @Value("{FileUploadApp.file.defaultPath}")
    private String defaultPath;

}
