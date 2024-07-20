package me.shinsunyoung.springbootdeveloper.domain;

import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

@Table(name = "users")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", updatable = false)
    private Long id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password")
    private String password;

    //9장 JWT로 로그인/로그아웃 구현하기 위해 사용
//    @Builder
//    public User(String email, String password, String auth) {
//        this.email = email;
//        this.password = password;
//    }


    @Column(name = "nickname", unique = true)
    private String nickname;

    //10장 OAuth2로 로그인/로그아웃 구현하기 위해 사용
    @Builder
    public User(String email, String password, String nickname) {
        this.email = email;
        this.password = password;
        this.nickname = nickname;
    }

    //권한 반환
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities(){
        return List.of(new SimpleGrantedAuthority("user"));
    }

    @Override
    public String getUsername() {
        return email;
    }

    //getPassword를 오버라이드를 통해 불러오지 않으면 암호화 되어있지 않은 비밀번호를 불러옴 => 보안상 이유로 암호화 되어있는 비밀번호를 가져오는 getPassword를 오버라이드를 통해 새로 만들어주어야 함
    @Override
    public String getPassword() {
        return password;
    }

    //계정 만료 여부 반환
    @Override
    public boolean isAccountNonExpired(){
        return true;
    }

    //계정 잠금 여부 반환
    @Override
    public boolean isAccountNonLocked(){
        return true;
    }

    //패스워드의 만료 여부 반환
    @Override
    public boolean isCredentialsNonExpired(){
        return true;
    }

    //계정 사용 가능 여부 반환
    @Override
    public boolean isEnabled(){
        return true;
    }

    public User update(String nickname) {
        this.nickname = nickname;

        return this;
    }
}
