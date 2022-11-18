package com.course.selection.app.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.course.selection.app.entity.UniversityStaffMember;
import com.course.selection.app.exception.AdminNotFoundException;
import com.course.selection.app.exception.StaffNotFoundException;
import com.course.selection.app.repository.UniversityStaffMemberRepository;
import com.course.selection.app.service.UniversityStaffMemberService;

@Service
public class UniversityStaffMemberServiceImpl implements UniversityStaffMemberService {
	@Autowired
	private UniversityStaffMemberRepository universityStaffMemberRepository;

	@Override
	public UniversityStaffMember addStaff(UniversityStaffMember staff) {

		return universityStaffMemberRepository.save(staff);
	}

	@Override
	public UniversityStaffMember updateStaff(UniversityStaffMember staff, Integer staffId) {
		UniversityStaffMember staffUpdated = universityStaffMemberRepository.findById(staffId).orElse(null);
		if (!ObjectUtils.isEmpty(staffUpdated)) {
			staffUpdated.setStaffId(staff.getStaffId());
			staffUpdated.setRole(staff.getRole());
			staffUpdated.setPassword(staff.getPassword());
			universityStaffMemberRepository.save(staffUpdated);
		}
		return staffUpdated;
	}

	@Override
	public UniversityStaffMember viewStaff(Integer staffId) {
		UniversityStaffMember staffOpt = universityStaffMemberRepository.findById(staffId).orElse(null);
		if (staffOpt == null) {
			throw new StaffNotFoundException("StaffId (" + staffId + ") Not Found");
		} else {
			return staffOpt;
		}

	}

	@Override
	public void removeStaff(Integer staffId) {

		universityStaffMemberRepository.deleteById(staffId);
	}

	@Override
	public List<UniversityStaffMember> viewAllStaffs() {
		return universityStaffMemberRepository.findAll();
	}

}
