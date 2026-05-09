package com.ucs.service.user;

import com.ucs.payload.request.updateRequest.TeacherUpdateByManagerRequest;
import com.ucs.payload.request.user.TeacherRequest;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.response.user.TeacherResponse;
import com.ucs.payload.response.user.UserResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface ITeacherService {
    TeacherResponse saveTeacher(TeacherRequest teacherRequest);

    List<StudentResponse> getAllStudentByAdvisorUserName();

    TeacherResponse updateTeacherForManagers(TeacherUpdateByManagerRequest teacherRequest, Long userId);

    UserResponse saveAdvisorTeacher(Long teacherId);

    UserResponse deleteAdvisorTeacherById(Long teacherId);

    Page<UserResponse> getAllAdvisorTeacher(int page, int size, String sort, String type);
}
