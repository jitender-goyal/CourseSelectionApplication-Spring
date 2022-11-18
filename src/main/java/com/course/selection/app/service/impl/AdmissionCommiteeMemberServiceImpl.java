package com.course.selection.app.service.impl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.course.selection.app.constants.AdmissionStatus;
import com.course.selection.app.entity.Admission;
import com.course.selection.app.entity.AdmissionCommiteeMember;
import com.course.selection.app.entity.Applicant;
import com.course.selection.app.exception.AdminNotFoundException;
import com.course.selection.app.exception.ApplicantNotFoundException;
import com.course.selection.app.exception.AdmissionSelectionException;
import com.course.selection.app.repository.AdmissionCommiteeMemberRepository;
import com.course.selection.app.repository.AdmissionRepository;
import com.course.selection.app.repository.ApplicantRepository;
import com.course.selection.app.service.AdmissionCommiteeMemberService;

@Service
public class AdmissionCommiteeMemberServiceImpl implements AdmissionCommiteeMemberService{

	@Autowired
	private AdmissionCommiteeMemberRepository admissionCommiteeMemberRepository;
	
	@Autowired
	private AdmissionRepository admissionRepository;
	
	@Autowired
	private ApplicantRepository applicantRepository;
	
	
	@Override
	public AdmissionCommiteeMember addCommiteeMember(AdmissionCommiteeMember admissionCommiteeMember) {
		return admissionCommiteeMemberRepository.save(admissionCommiteeMember);
	}

	@Override
	public AdmissionCommiteeMember updateCommiteeMember(AdmissionCommiteeMember admissionCommiteeMember, Integer adminId) {
		AdmissionCommiteeMember updatedCommiteeMember = admissionCommiteeMemberRepository.findById(adminId).orElse(null);
		if(!ObjectUtils.isEmpty(updatedCommiteeMember)) {
			updatedCommiteeMember.setAdminName(admissionCommiteeMember.getAdminName());
			updatedCommiteeMember.setAdminContact(admissionCommiteeMember.getAdminContact());
			admissionCommiteeMemberRepository.save(updatedCommiteeMember);
		}		
		return updatedCommiteeMember;
	}

	@Override
	public AdmissionCommiteeMember viewCommiteeMember(Integer adminId) {
		AdmissionCommiteeMember admOpt = admissionCommiteeMemberRepository.findById(adminId).orElse(null);
			if(admOpt==null) {
				throw new AdminNotFoundException("AdminId ("+adminId+") Not Found");
			}else {
			return admOpt;
		}
	}

	@Override
	public void removeCommiteeMember(Integer adminId) {
		admissionCommiteeMemberRepository.deleteById(adminId);
		
	}

	@Override
	public List<AdmissionCommiteeMember> viewAllCommiteeMembers() {
		return admissionCommiteeMemberRepository.findAll();
	}

	@Override
	public void provideAdmissionResult(Long applicantID, Admission admission) {
		
		Optional<Applicant> applicant = applicantRepository.findById(applicantID);
		List<Admission> admissions =  admissionRepository.findByApplicant(applicant.get());
		admissions.forEach(adm -> {
			if(adm.getStatus()== AdmissionStatus.APPLIED && admission.getStatus() == AdmissionStatus.PENDING ) {

				adm.setStatus(admission.getStatus());
				admissionRepository.saveAll(admissions);
				
			
			}
			else if(adm.getStatus()== AdmissionStatus.PENDING && 
					(admission.getStatus() == AdmissionStatus.CONFIRMED || 
					admission.getStatus() == AdmissionStatus.REJECTED) ) {
				
				adm.setStatus(admission.getStatus());
				admissionRepository.saveAll(admissions);
				
			}
			else {
				throw new AdmissionSelectionException("Status Cannot be Updated");
			}
		});
		
	}


	

}
