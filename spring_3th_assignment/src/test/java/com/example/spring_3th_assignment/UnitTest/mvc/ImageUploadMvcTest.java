package com.example.spring_3th_assignment.UnitTest.mvc;


import com.example.spring_3th_assignment.Configuration.SecurityConfiguration;
import com.example.spring_3th_assignment.Controller.MemberController;
import com.example.spring_3th_assignment.Controller.PostController;
import com.example.spring_3th_assignment.Controller.S3Controller;
import com.example.spring_3th_assignment.Controller.request.LoginRequestDto;
import com.example.spring_3th_assignment.Controller.request.PostRequestDto;

import com.example.spring_3th_assignment.domain.Member;
import com.example.spring_3th_assignment.domain.UserDetailsImpl;
import com.example.spring_3th_assignment.jwt.JwtFilter;
import com.example.spring_3th_assignment.jwt.TokenProvider;
import com.example.spring_3th_assignment.repository.*;
import com.example.spring_3th_assignment.service.MemberService;
import com.example.spring_3th_assignment.service.PostService;
import com.example.spring_3th_assignment.service.S3UploaderService;
import com.example.spring_3th_assignment.service.UserDetailsServiceImpl;
import com.example.spring_3th_assignment.util.ImageScheduler;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.FilterType;
import org.springframework.data.jpa.mapping.JpaMetamodelMappingContext;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.web.context.WebApplicationContext;

import java.security.Principal;

import static org.springframework.security.test.web.servlet.setup.SecurityMockMvcConfigurers.springSecurity;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

//@WebMvcTest(
//        controllers = {S3Controller.class, PostController.class, MemberController.class},
//        excludeFilters = {
//                @ComponentScan.Filter(
//                        type = FilterType.ASSIGNABLE_TYPE,
//                        classes = SecurityConfiguration.class
//                )
//        }
//)

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class ImageUploadMvcTest {

    private MockMvc mvc;

    private Principal mockPrincipal;
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    S3UploaderService s3UploaderService;

    @MockBean
    ImageScheduler imageScheduler;

    @MockBean
    private PostRepository postRepository;

    @MockBean
    private CommentRepository commentRepository;

    @MockBean
    private  ReCommentRepository reCommentRepository;
    @MockBean
    private  PostLikeRepository postLikeRepository;

    private String access_token="";
    private String refresh_token="";


    private String SECRET_KEY="4oCYc3ByaW5nLWJvb3Qtc2VjdXJpdHktand0LWhhbmdoYWUtYXNzaWdubWVudC1zcHJpbmctYm9vdC1zZWN1cml0eS1qd3Qtc2VjcmV0LWtleeKAmQo=";
    @Autowired
    private TokenProvider tokenProvider;
    @Autowired
    private  UserDetailsServiceImpl userDetailsService;


    @BeforeEach
    public void setup(){
        mvc = MockMvcBuilders.webAppContextSetup(context)
                .apply(springSecurity(new JwtFilter(SECRET_KEY, tokenProvider, userDetailsService)))
                .build();
    }


    private void mockMemberSetup(){
        String nickname = "winsomed96";
        String password = "hosung114";


        Member member = new Member(nickname,password);
        UserDetailsImpl testUserDetails = new UserDetailsImpl(member);
        mockPrincipal = new UsernamePasswordAuthenticationToken(testUserDetails,"",null);
    }

    @Test
    @Order(1)
    @DisplayName("로그인")
    void loginMVCTest()throws Exception{

        String nickname = "winsomed96";
        String password = "hosung1194";

        //로그인
        LoginRequestDto loginRequestDto = new LoginRequestDto(nickname,password);

        String loginInfo = objectMapper.writeValueAsString(loginRequestDto);
        MvcResult mvcResult = mvc.perform(post("/api/member/login")
                .content(loginInfo)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
        )
                .andExpect(status().isOk())
                .andDo(print())
                .andReturn();


        access_token=  mvcResult.getResponse().getHeader("Authorization");
        refresh_token= mvcResult.getResponse().getHeader("Refresh-Token");
        System.out.println(refresh_token);
        System.out.println(access_token);
    }
    @Test
    @Order(2)
    @DisplayName("게시글 작성")
    void postUpload()throws Exception{


        this.mockMemberSetup();
        String title = "제목";
        String imageUrl= "https://shopping-phinf.pstatic.net/main_1862208/18622086330.20200831140839.jpg";
        String content = "내용";

        PostRequestDto postRequestDto = new PostRequestDto(title,content,imageUrl);
//        AllPostResponseDto postResponseDto2 = new AllPostResponseDto()

        String postInfo = objectMapper.writeValueAsString(postRequestDto);

        mvc.perform(post("/api/auth/post")
                        .content(postInfo)
                        .header("Authorization",access_token)
                        .header("Refresh-Token",refresh_token)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                )
                .andExpect(status().isOk())
                .andDo(print());
    }

}
