package fixel.cs.service;

import fixel.cs.dto.user.UserAddRequest;
import fixel.cs.dto.user.UserLoginRequest;
import fixel.cs.entity.User;
import fixel.cs.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    // 회원 등록
    @Transactional
    public ResponseEntity addUser(UserAddRequest request) {

        if (!userRepository.findByUserEmail(request.getUserEmail()).isPresent()) {
            User user = User.builder()
                    .userEmail(request.getUserEmail())
                    .password(request.getPassword())
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

        Optional<User> user = userRepository.findByUserEmailAndPassword(request.getUserEmail(), request.getPassword());

        if (user.isPresent()) {
            return ResponseEntity.ok(user);
        } else {
            throw new IllegalArgumentException("해당하는 회원이 없습니다.");
        }
    }
}
