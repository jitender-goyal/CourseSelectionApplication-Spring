package com.course.selection.app.service.impl;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;
import java.util.List;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import com.course.selection.app.constants.AdmissionStatus;
import com.course.selection.app.entity.Admission;
import com.course.selection.app.entity.Applicant;
import com.course.selection.app.entity.Course;
import com.course.selection.app.exception.AdmissionSelectionException;
import com.course.selection.app.repository.AdmissionRepository;
import com.course.selection.app.service.AdmissionService;

@Service
public class AdmissionServiceImpl implements AdmissionService {
	@Autowired
	private AdmissionRepository admissionRepository;
	
	@Override
	public Admission saveAdmissionDetails(Admission admission) {
		
		if(admission.getAdmissionDate().isAfter(LocalDate.now()) || admission.getAdmissionDate().equals(LocalDate.now()))
		{
		
		return admissionRepository.save(admission);
		}
		else 
			throw new AdmissionSelectionException("Enter Correct date.");
	}

	@Override
	public Admission updateAdmissionDetails(Admission admission, Long admissionId) {
		Admission admissionUpdated = admissionRepository.findById(admissionId).orElse(null);
		if(!ObjectUtils.isEmpty(admissionUpdated)) {
			if(admission.getAdmissionDate().isAfter(LocalDate.now()) || admission.getAdmissionDate().equals(LocalDate.now()))
			{
			admissionUpdated.setAdmissionDate(admission.getAdmissionDate());
			return admissionRepository.save(admissionUpdated);
			
			}
			else 
				throw new AdmissionSelectionException("Enter Correct date.");
			
			
		}		
		return admissionUpdated;
	}

	@Override
	public List<Admission> getAllAdmissionDetails() {
		
		return admissionRepository.findAll();
	}

	@Override
	public Admission getAdmissionDetails(Long admissionId) {
		return admissionRepository.findById(admissionId).orElse(null);
	}

	@Override
	public void deleteAdmissionDetails(Long admissionId) {
		admissionRepository.deleteById(admissionId);
		
	}

	@Override
	public List<Admission> showAllAdmissionDetailsByCourseId(Long courseId) {
		List<Admission> admissions = admissionRepository.findAll();
		List<Admission> l = admissions.stream().filter(admin -> admin.getCourse().getCourseId() == courseId).collect(Collectors.toList());
		return l;
	}

	@Override
	public List<Admission> showAllAdmissionDetailsByDate(String admissionDate) {
		List<Admission> admissions = admissionRepository.findAll();
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");		 
		 //convert String to LocalDate
		 LocalDate localDate = LocalDate.parse(admissionDate, formatter);
		admissions.stream().filter(admin -> (admin.getAdmissionDate()).compareTo(localDate) == 0 ).collect(Collectors.toList());
		return admissions;
		
	}
	
	@Override
	public List<Admission> ViewAllApplicantsByStatus(AdmissionStatus status) {
		List<Admission> applicants = admissionRepository.findAll();
		List<Admission> l =applicants.stream().filter(admin -> (admin.getStatus()).compareTo(status) == 0 ).collect(Collectors.toList());
		return l;
		
	}
	
	

}
