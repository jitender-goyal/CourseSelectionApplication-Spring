package com.course.selection.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.selection.app.entity.Applicant;


public interface ApplicantRepository extends JpaRepository<Applicant, Long> {

}
