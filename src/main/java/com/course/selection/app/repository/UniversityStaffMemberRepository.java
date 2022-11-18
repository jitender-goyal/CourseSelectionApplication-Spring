package com.course.selection.app.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.course.selection.app.entity.Applicant;
import com.course.selection.app.entity.UniversityStaffMember;

public interface UniversityStaffMemberRepository extends JpaRepository<UniversityStaffMember, Integer> {

	
}
