package com.newboard.newboard.domain;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;


@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Data
@ToString
public class User implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userid")
    private Long userid;      // 유저pk

    @Column(name = "email",unique = true, nullable = false)
    private String email;     // 로그인 ID

    @Column(name = "password", nullable = false)
    private String password;    // 로그인 비밀번호

    @Column(name = "nickname", nullable = false)
    private String nickname;    // 유저실명

    @Enumerated(EnumType.STRING)//DB에 Enum 값이 그대로 String으로 저장
    private Role role;


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("USER"));
    }

    //사용자의 id를 반환(고유한 값)
    @Override
    public String getUsername(){
        return email;
    }
    //사용자 패스워드 반환
    @Override
    public String getPassword(){
        return password;
    }

    @Builder
    public User(String email, String password, String nickname, Role role) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
        this.role = role;
    }


}
