package com.example.spring_3th_assignment.Controller;

import com.example.spring_3th_assignment.Controller.request.PostRequestDto;
import com.example.spring_3th_assignment.Controller.response.ResponseDto;
import com.example.spring_3th_assignment.service.PostService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;

@RequiredArgsConstructor
@RestController
public class PostController {

    private final PostService postService;

    @RequestMapping(value = "/api/auth/post", method = RequestMethod.POST)
    public ResponseDto<?> createPost(@RequestBody PostRequestDto requestDto,
                                     HttpServletRequest request) {
        return postService.createPost(requestDto, request);
    }

    @RequestMapping(value = "/api/post/{id}", method = RequestMethod.GET)
    public ResponseDto<?> getPost(@PathVariable Long id) {
        return postService.getPost(id);
    }

    @RequestMapping(value = "/api/post", method = RequestMethod.GET)
    public ResponseDto<?> getAllPosts() {
        return postService.getAllPost();
    }

    @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.PUT)
    public ResponseDto<?> updatePost(@PathVariable Long id, @RequestBody PostRequestDto postRequestDto,
                                     HttpServletRequest request) {
        return postService.updatePost(id, postRequestDto, request);
    }

    @RequestMapping(value = "/api/auth/post/{id}", method = RequestMethod.DELETE)
    public ResponseDto<?> deletePost(@PathVariable Long id,
                                     HttpServletRequest request) {
        return postService.deletePost(id, request);
    }

}
