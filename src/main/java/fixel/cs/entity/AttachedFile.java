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
    private Long id;
    private String fileName;
    private String filePath;
    private LocalDateTime regDt;

    @ManyToOne
    @JoinColumn(name = "request_id")
    private Request request;
}
