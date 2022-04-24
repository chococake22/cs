package fixel.cs.controller;

import fixel.cs.dto.user.UserAddRequest;
import fixel.cs.dto.user.UserLoginRequest;
import fixel.cs.service.UserService;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @ApiOperation(value = "회원 생성", notes = "회원을 생성합니다.")
    @PostMapping("/signup")
    public ResponseEntity addUser(@RequestBody UserAddRequest request) {
        return userService.addUser(request);
    }

    @ApiOperation(value = "로그인", notes = "로그인을 합니다.")
    @PostMapping("/login")
    public ResponseEntity login(@RequestBody UserLoginRequest request) {
        return userService.login(request);
    }
}
