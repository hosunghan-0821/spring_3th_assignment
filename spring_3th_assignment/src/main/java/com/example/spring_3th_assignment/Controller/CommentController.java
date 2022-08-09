package com.example.spring_3th_assignment.Controller;

import com.example.spring_3th_assignment.Controller.request.CommentRequestDto;
import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.security.Principal;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    // 댓글 작성
    @RequestMapping(value = "/api/auth/comment", method = RequestMethod.POST)
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.createComment(requestDto, request);
    }

    // 댓글 조회 // 아이디는 포스틑 아이디
    @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllComments(@PathVariable Long id) {
        return commentService.getAllCommentsByPost(id);
    }

    // 댓글 수정
    @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }

    // 댓글 삭제
    @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }


    @PostMapping("/api/comment/{id}//like")
    @PreAuthorize("isAuthenticated()")
    public SuccessResponse<String> commentLike(@PathVariable(name = "id") Long commentId, Principal principal) {
        commentService.commentLike(commentId, principal.getName());
        return SuccessResponse.success(null);
    }
}