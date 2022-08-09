package com.example.spring_3th_assignment.Controller;

import com.example.spring_3th_assignment.Controller.request.ReCommentRequestDto;
import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.service.ReCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;


@Validated
@RequiredArgsConstructor
@RestController
public class ReCommentController {

    private final ReCommentService reCommentService;

    // 생성 / 로그인 필요
    @RequestMapping(value = "/api/auth/recomment", method = RequestMethod.POST)
    public ResponseDto<?> createReComment(@RequestBody ReCommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return reCommentService.createReComment(requestDto, request);
    }

    //대댓글 조회
    @RequestMapping(value = "/api/recomment/{commentId}", method = RequestMethod.GET)
    public ResponseDto<?> getAllSubComments(@PathVariable Long commentId){
        return reCommentService.getAllReCommentByComment(commentId);
    }

    // 수정 / 로그인 필요
    @RequestMapping(value = "/api/auth/recomment/{commentId}", method = RequestMethod.PUT)
    public ResponseDto<?> updateSubComment(@PathVariable Long commentId, @RequestBody ReCommentRequestDto RequestDto,
                                           HttpServletRequest request) {
        return reCommentService.updateReComment(commentId, RequestDto, request);
    }

    // 삭제 / 로그인 필요
    @RequestMapping(value = "/api/auth/recomment/{commentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long commentId,
                                        HttpServletRequest request) {
        return reCommentService.deleteReComment(commentId, request);
    }
}
