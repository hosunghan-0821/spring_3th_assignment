package com.example.spring_3th_assignment.Controller;

import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.service.MyPageService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@RequiredArgsConstructor
@RestController
public class MyPageController {

    private final MyPageService myPageService;

    //마이페이지
    //로그인한 유저가 작성한 게시글,댓글,대댓글 보임 / 추후 좋아요한 게시글,댓글 업데이트 필요
    @RequestMapping(value = "/api/mypage", method = RequestMethod.GET)
    public ResponseDto<?> getAllMyLog(HttpServletRequest request) {
        return myPageService.getAllMyLog(request);
    }

}
