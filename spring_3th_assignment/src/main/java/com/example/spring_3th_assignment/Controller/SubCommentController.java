package com.example.spring_3th_assignment.Controller;

import com.example.spring_3th_assignment.Controller.request.SubCommentRequestDto;
import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.service.SubCommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class SubCommentController {

    private final SubCommentService subCommentService;

    //대댓글 작성
    @RequestMapping(value = "/api/auth/subcomment", method = RequestMethod.POST)
    public ResponseDto<?> createSubComment(@RequestBody SubCommentRequestDto subCommentRequestDto, HttpServletRequest request ) {
        return subCommentService.createSubComment(subCommentRequestDto, request);
    }

    //대댓글 조회
    @RequestMapping(value = "/api/subcomment/{commentId}", method = RequestMethod.GET)
    public ResponseDto<?> getAllSubComments(@PathVariable Long commentId){
        return subCommentService.getAllSubCommentsByComment(commentId);
    }

    @RequestMapping(value = "/api/auth/subcomment/{commentId}", method = RequestMethod.PUT)
    public ResponseDto<?> updateSubComment(@PathVariable Long commentId, @RequestBody SubCommentRequestDto subCommentRequestDto,
                                           HttpServletRequest request) {
        return subCommentService.updateSubComment(commentId, subCommentRequestDto, request);
    }

    @RequestMapping(value = "/api/auth/subcomment/{commentId}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long commentId,
                                        HttpServletRequest request) {
        return subCommentService.deleteSubComment(commentId, request);
    }
}
