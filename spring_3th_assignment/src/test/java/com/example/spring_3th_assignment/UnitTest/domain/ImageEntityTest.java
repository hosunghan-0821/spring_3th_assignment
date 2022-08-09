package com.example.spring_3th_assignment.UnitTest.domain;


import com.example.spring_3th_assignment.domain.Image;
import com.example.spring_3th_assignment.domain.Post;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ImageEntityTest {

    @Nested
    @DisplayName("이미지 DB저장 객체 생성")
    class CreateImage{

        private Post post;
        private String imgURL;

        @BeforeEach
        void setup(){
            post = Post.builder()
                    .id(1L)
                    .title("제목")
                    .content("상세내용")
                    .build();

            imgURL="https://hosunghan.s3.ap-northeast-2.amazonaws.com/static/b9a68c15-31bb-42a8-8c18-bbfd96e89edf20220806_193636.jpg";
        }

        @Test
        @DisplayName("정상케이스")
        void createImage_Normal(){

            //given -> setup()

            //when
            Image image = new Image(post,imgURL);

            //then
            assertEquals(post.getId(),image.getPost().getId());
            assertEquals(imgURL,image.getImgURL());


        }

        @Nested
        @DisplayName("실패 케이스")
        class FailCases{
            @Nested
            @DisplayName("PostId")
            class postId{
                @Test
                @DisplayName("null")
                void failByNull(){
                    //given
                    post.setId(null);
                    //when


                    Exception exception = assertThrows(IllegalArgumentException.class , ()->{
                        new Image(post,imgURL);
                    });
                    // then
                    assertEquals("유효하지 않는 Post Id입니다.",exception.getMessage());
                }
                @Test
                @DisplayName("minus")
                void failByMinus(){

                    //given
                    post.setId(-1L);
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class , ()->{
                        new Image(post,imgURL);
                    });
                    // then
                    assertEquals("유효하지 않는 Post Id입니다.",exception.getMessage());
                }
            }

            @Nested
            @DisplayName("ImgUrl")
            class ImgUrl{
                @Test
                @DisplayName("ImgUrl : null")
                void failByUrl(){

                    //given
                    imgURL=null;

                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class , ()->{
                        new Image(post,imgURL);
                    });
                    //then
                    assertEquals("imgUrl 이 유효하지 않습니다",exception.getMessage());
                }

                @Test
                @DisplayName("ImgUrl : 포맷형태 틀림")
                void failByUrlFormat(){

                    imgURL="shopping-phinf.pstatic.net/main_2416122/24161228524.20200915151118.jpg";
                    //when
                    Exception exception = assertThrows(IllegalArgumentException.class , ()->{
                        new Image(post,imgURL);
                    });
                    //then
                    assertEquals("imgUrl 이 유효하지 않습니다",exception.getMessage());

                }
            }
        }
    }


}