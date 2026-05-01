package com.ucs.service.user;

import com.ucs.payload.request.business.ChooseLessonProgramWithId;
import com.ucs.payload.request.updateRequest.StudentUpdateByAdminRequest;
import com.ucs.payload.request.updateRequest.StudentUpdateRequest;
import com.ucs.payload.request.user.StudentRequest;
import com.ucs.payload.response.user.StudentResponse;
import com.ucs.payload.response.user.UserResponse;

public interface IStudentService {
    StudentResponse saveStudent(StudentRequest studentRequest, UserResponse authenticatedUser);
    String changeStatusOfStudent(Long studentId, Boolean status);
    String updateStudent(StudentUpdateRequest studentRequest, UserResponse authenticatedUser);
    StudentResponse updateStudentForManager(Long userId,
                                            StudentUpdateByAdminRequest studentRequest,
                                            UserResponse authenticatedUser);
    StudentResponse addLessonProgramToStudent(ChooseLessonProgramWithId chooseLessonProgramWithId, String username);
}
