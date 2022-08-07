package com.example.spring_3th_assignment.repository;

import com.example.spring_3th_assignment.domain.Image;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ImageRepository extends JpaRepository<Image,Long> {

    Optional<Image> findByImgURL(String imgUrl);
}
