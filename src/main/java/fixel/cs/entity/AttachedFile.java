package fixel.cs.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "AttachedFile")
@Table(name = "AttachedFile")
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AttachedFile {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "attachedfile_no")
    private Long attachedFileNo;
    private String fileName;

    @JsonIgnore
    private String fileId;

    @JsonIgnore
    private String filePath;
    private LocalDateTime regDt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "question_no")
    @JsonIgnore
    private Question question;


}
