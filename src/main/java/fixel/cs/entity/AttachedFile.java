package fixel.cs.entity;

import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "AttachedFile")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttachedFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachedfile_no")
    private Long no;
    private String fileName;
    private String fileId;
    private String filePath;
    private LocalDateTime regDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "request_no")
    private Request request;


}
