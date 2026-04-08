package code.engineer.lufit.domain.user.entity;

import code.engineer.lufit.global.enums.FileExtension;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profile_files")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfileFile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "extension")
    @Enumerated(EnumType.STRING)
    private FileExtension extension;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "path", length = 500)
    private String path;

    @Column(name = "file_uuid", length = 500)
    private String fileUuid;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private UserProfileFile(Users user, FileExtension extension, String name, String path, String fileUuid) {
        this.user = user;
        this.extension = extension;
        this.name = name;
        this.path = path;
        this.fileUuid = fileUuid;
    }

    public static UserProfileFile createJPGFile(Users user, String name, String path, String fileUuid) {
        return new UserProfileFile(user, FileExtension.JPG, name, path, fileUuid);
    }

    public static UserProfileFile createPNGFile(Users user, String name, String path, String fileUuid) {
        return new UserProfileFile(user, FileExtension.PNG, name, path, fileUuid);
    }
}
