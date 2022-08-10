package com.example.spring_3th_assignment.UnitTest.mvc;


import com.example.spring_3th_assignment.Configuration.SecurityConfiguration;
import com.example.spring_3th_assignment.Controller.PostController;
import com.example.spring_3th_assignment.Controller.S3Controller;
import com.example.spring_3th_assignment.Controller.request.PostRequestDto;

import com.example.spring_3th_assignment.domain.Member;
import com.example.spring_3th_assignment.domain.UserDetailsImpl;
import com.example.spring_3th_assignment.service.PostService;
import com.example.spring_3th_assignment.service.S3UploaderService;
import com.example.spring_3th_assignment.util.ImageScheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@WebMvcTest(
        controllers = {S3Controller.class, PostController.class},
        excludeFilters = {
                @ComponentScan.Filter(
                        type = FilterType.ASSIGNABLE_TYPE,
                        classes = SecurityConfiguration.class
                )
        }
)



public class ImageUploadMvcTest {

    private MockMvc mvc;

    private Principal mockPrincipal;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    JpaMetamodelMappingContext jpaMetamodelMappingContext;

    @MockBean
    S3UploaderService s3UploaderService;

    @MockBean
    PostService postService;

    @MockBean
    ImageScheduler imageScheduler;

    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new MockSpringSecurityFilter()))
                .build();
    }

    private void mockMemberSetup(){
        String nickname = "한호성";
        String password = "hosung1194";

        Member member = new Member(nickname,password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(member);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails,"",null);
    }

    @Test
    @DisplayName("게시글 작성 mvc test")
    void uploadMVCTest()throws Exception{

        this.mockMemberSetup();
        String title = "제목";
        String imageUrl= "https://shopping-phinf.pstatic.net/main_1862208/18622086330.20200831140839.jpg";
        String content = "내용";

        PostRequestDto postRequestDto = new PostRequestDto(title,content,imageUrl
        );
//        AllPostResponseDto postResponseDto2 = new AllPostResponseDto()

        String postInfo = objectMapper.writeValueAsString(postRequestDto);

        mvc.perform(post("/api/auth/post")
                .content(postInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .principal(mockPrincipal)
        )
                .andExpect(status().isOk())
                .andDo(print());
    }
}
