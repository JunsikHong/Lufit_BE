package code.engineer.lufit.domain.user.entity;

import code.engineer.lufit.domain.user.enums.Gender;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;
import org.springframework.cglib.core.Local;

import java.time.LocalDateTime;

@Entity
@Table(name = "user_profiles")
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UserProfile {

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private Users user;

    @Column(name = "name", length = 500)
    private String name;

    @Column(name = "birth")
    private LocalDateTime birth;

    @Column(name = "gender", length = 10)
    @Enumerated(EnumType.STRING)
    private Gender gender;

    @Column(name = "nationality", length = 500)
    private String nationality;

    @Column(name = "phone", length = 500)
    private String phone;

    @Column(name = "created_at")
    @CreationTimestamp
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    @UpdateTimestamp
    private LocalDateTime updatedAt;

    private UserProfile(Users user, String name, LocalDateTime birth, Gender gender, String nationality, String phone) {
        this.user = user;
        this.name = name;
        this.birth = birth;
        this.gender = gender;
        this.nationality = nationality;
        this.phone = phone;
    }

    public static UserProfile createMale(Users user, String name, LocalDateTime birth, String nationality, String phone) {
        return new UserProfile(user, name, birth, Gender.M, nationality, phone);
    }

    public static UserProfile createFemale(Users user, String name, LocalDateTime birth, String nationality, String phone) {
        return new UserProfile(user, name, birth, Gender.F, nationality, phone);
    }
}