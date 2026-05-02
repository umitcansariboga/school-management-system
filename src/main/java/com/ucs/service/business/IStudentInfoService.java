package com.ucs.service.business;

import com.ucs.payload.request.business.StudentInfoRequest;
import com.ucs.payload.request.business.UpdateStudentInfoRequest;
import com.ucs.payload.response.business.StudentInfoResponse;
import com.ucs.payload.response.user.UserResponse;
import org.springframework.data.domain.Page;

public interface IStudentInfoService {
    StudentInfoResponse saveStudentInfo(StudentInfoRequest studentInfoRequest, UserResponse authenticatedUser);

    String deleteById(Long studentInfoId);

    StudentInfoResponse update(UpdateStudentInfoRequest studentInfoRequest, Long studentInfoId);

    Page<StudentInfoResponse> getAllForTeacher(UserResponse authenticatedUser, int page, int size);

    Page<StudentInfoResponse> getAllForStudent(UserResponse authenticatedUser, int page, int size);
}
