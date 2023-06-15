package kr.dklog.admin.dklogadmin.entity;

import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Image {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long imageId;

    private String uploadName;

    private String location;

    private String storeName;

    private String fileType;

    @ManyToOne
    @JoinColumn(name = "post_id")
    private Post post;

    @Builder
    public Image(Long imageId, String uploadName, String location, String storeName, String fileType, Post post) {
        this.imageId = imageId;
        this.uploadName = uploadName;
        this.location = location;
        this.storeName = storeName;
        this.fileType = fileType;
        this.post = post;
    }
}
