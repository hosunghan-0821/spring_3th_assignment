package com.example.spring_3th_assignment.Controller;

import com.example.spring_3th_assignment.Controller.request.CommentRequestDto;
import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.service.CommentService;
import lombok.RequiredArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@Validated
@RequiredArgsConstructor
@RestController
public class CommentController {

    private final CommentService commentService;

    @RequestMapping(value = "/api/auth/comment", method = RequestMethod.POST)
    public ResponseDto<?> createComment(@RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.createComment(requestDto, request);
    }

    @RequestMapping(value = "/api/comment/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getAllComments(@PathVariable Long id) {
        return commentService.getAllCommentsByPost(id);
    }

    @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updateComment(@PathVariable Long id, @RequestBody CommentRequestDto requestDto,
                                        HttpServletRequest request) {
        return commentService.updateComment(id, requestDto, request);
    }

    @RequestMapping(value = "/api/auth/comment/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deleteComment(@PathVariable Long id,
                                        HttpServletRequest request) {
        return commentService.deleteComment(id, request);
    }
}