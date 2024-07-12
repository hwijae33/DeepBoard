package com.newboard.newboard.service;

import com.newboard.newboard.domain.Role;
import com.newboard.newboard.domain.User;
import com.newboard.newboard.dto.SignUpFormDTO;
import com.newboard.newboard.repository.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@Transactional
@RequiredArgsConstructor
@Log4j2
public class UserServiceImpl implements UserDetailsService {

    private final UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        Optional<User> optionalUser = userRepository.findByEmail(email);
        User user = optionalUser.orElseThrow(() -> new UsernameNotFoundException("사용자를 찾을 수 없습니다."));

        // 권한(authorities) 설정
        List<GrantedAuthority> authorities = new ArrayList<>();
        if ("admin".equals(email)) {
            authorities.add(new SimpleGrantedAuthority(Role.ADMIN.getRoleUser()));
        } else {
            authorities.add(new SimpleGrantedAuthority(Role.USER.getRoleUser()));
        }

        if(user != null) {

            return new UserPrincipalDetails(user);
//            return org.springframework.security.core.userdetails.User.builder()
//                    .username(user.getEmail())
//                    .password(user.getPassword())
//                    .roles(user.getRole().name())
//                    .build();
        }
        return null;
    }


    @Transactional
    public Long save(SignUpFormDTO sign) {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
        String encodedPassword = encoder.encode(sign.getPassword());

        Role userRole = Role.USER; // 기본값 설정


        User user = User.builder()
                .email(sign.getEmail())
                .password(encodedPassword)
                .nickname(sign.getNickname())
                .role(userRole)
                .build();

        return userRepository.save(user).getUserid(); // 또는 다른 필요한 ID 값을 반환할 수 있습니다.
    }


    public User existsByEmail(String email){

        return userRepository.existsByEmail(email);
    }
}

