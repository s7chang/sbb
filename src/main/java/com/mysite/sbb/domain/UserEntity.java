package com.mysite.sbb.domain;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Entity
@Table(name = "site_user")
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(unique = true, columnDefinition = "varchar(50)")
    @NotBlank(message = "사용자 ID는 필수 항목입니다.")
	@Size(min=3, max = 25)
    private String username;

    @NotBlank(message = "비밀번호는 필수 항목입니다.")
    private String password;

    // 폼을 위한 필드
    @Transient
    @NotBlank(message = "비밀번호 확인은 필수 항목입니다.")
    private String password2;

    @Column(unique = true, columnDefinition = "varchar(50)")
    @NotBlank(message = "이메일은 필수 항목입니다.")
    private String email;
}
