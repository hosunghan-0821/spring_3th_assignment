package com.example.spring_3th_assignment.Validator;

import com.example.spring_3th_assignment.domain.Post;

public class  ValidateObject  {


    public static void  postValidate(Post post){
        if(post.getId()==null || post.getId()<=0){
            throw  new IllegalArgumentException("유효하지 않는 Post Id입니다.");
        }
    }
}
