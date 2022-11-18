package com.course.selection.app.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.course.selection.app.constants.AdmissionStatus;
import com.course.selection.app.entity.Admission;
import com.course.selection.app.entity.Applicant;
import com.course.selection.app.exception.ApplicantNotFoundException;
import com.course.selection.app.repository.ApplicantRepository;
import com.course.selection.app.service.ApplicantService;

@Service
public class ApplicantServiceImpl implements ApplicantService  {
	
	@Autowired
	private ApplicantRepository applicantRepository;

	@Override
	public Applicant saveApplicantDetails(Applicant applicant) {
		return applicantRepository.save(applicant);
	}

	@Override
	public Applicant updateApplicantDetails(Applicant applicant, Long applicantId) {
		Applicant applicantUpdate = applicantRepository.findById(applicantId).orElse(null);
		//if(!ObjectUtils.isEmpty(applicantUpdated)) 
		if(applicantUpdate!=null){
			applicantUpdate.setApplicantName(applicant.getApplicantName());
			applicantUpdate.setMobileNumber(applicant.getMobileNumber());
			applicantUpdate.setApplicantDegree(applicant.getApplicantDegree());
			applicantUpdate.setApplicantGraduationPercent(applicant.getApplicantGraduationPercent());
			applicantRepository.save(applicantUpdate);
			return applicantUpdate;
		}
		else
		{
			throw new ApplicantNotFoundException("ApplicantId ("+applicantId+") Not Found");
		}
	}

	@Override
	public List<Applicant> getAllApplicantDetails() {
		return applicantRepository.findAll();
	}

	@Override
	public Applicant getApplicantDetails(Long applicantId) {
		Applicant applicantOpt = applicantRepository.findById(applicantId).orElse(null);
		if(applicantOpt==null) {
			throw new ApplicantNotFoundException("ApplicantId ("+applicantId+") Not Found");
		}else {
		return applicantOpt;
		}
	}

	@Override
	public void deleteApplicantDetails(Long applicantId)  {
		//applicantRepository.deleteById(applicantId);
		Optional<Applicant> applicantOpt = applicantRepository.findById(applicantId);
		if(!applicantOpt.isPresent()) {
			throw new ApplicantNotFoundException("ApplicantId ("+applicantId+") Not Found");
		}else {
			applicantRepository.deleteById(applicantId);
		}
	}

	
}
