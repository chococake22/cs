package fixel.cs.service;

import fixel.cs.dto.user.UserAddRequest;
import fixel.cs.dto.user.UserLoginRequest;
import fixel.cs.entity.Request;
import fixel.cs.entity.User;
import fixel.cs.repository.UserRepository;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    // 회원 등록

    // 1. 각각의 필드에 대한 검증 필요
    // 2. 필요한 정보만 받을 수 있도록
    // 3. 예외처리 (잘못 만들어진 경우, 저장이 안된 경우에 대한 대비)
    // 4. 리턴 타입에 대한 정리 -> 바로 이용하게 할지
    // 5. 회원가입 후 이메일 전송
    // 6. 토큰 활용

    @Transactional
    public ResponseEntity addUser(UserAddRequest request) {

        if (!userRepository.findByUserEmail(request.getUserEmail()).isPresent()) {
            User user = User.builder()
                    .userEmail(request.getUserEmail())
                    .password(passwordEncoder.encode(request.getPassword()))    // 임의 생성한 패스워드로 변경해서 회원에게 보내주기..
                    .username(request.getUsername())
                    .pwdChangedYn("n")
                    .build();

            userRepository.save(user);

            return ResponseEntity.ok().body(user);
        } else {
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
    }

    // 로그인
    @Transactional
    public ResponseEntity login(UserLoginRequest request) {

        // 상황별 상태에 맞는 로직이 필요하다 ...

















        User findUser = userRepository.getByUserEmail(request.getUserEmail());  // optional 추가(null)

        // Optional<User> user = userRepository.findByUserEmailAndPassword(request.getUserEmail(), request.getPassword());

        // 비밀번호 검증
        if (passwordEncoder.matches(request.getPassword(), findUser.getPassword())) {
            return ResponseEntity.ok(findUser); // 더 티테일한 상태 전달 필요 ex Notfound
        } else {
            throw new IllegalArgumentException("아이디나 비밀번호가 틀렸습니다.");    // 유저가 한 행동에 대한 응답으로 리턴
        }

        // 토큰 생성 - 계정 정보 유지
    }
}
