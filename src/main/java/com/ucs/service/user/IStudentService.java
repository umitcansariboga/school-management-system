package com.ucs.service.user;

import com.ucs.payload.request.business.ChooseLessonProgramWithId;
import com.ucs.payload.request.updateRequest.StudentUpdateByManagersRequest;
import com.ucs.payload.request.updateRequest.StudentUpdateRequest;
import com.ucs.payload.request.user.StudentRequest;
import com.ucs.payload.response.user.StudentResponse;

public interface IStudentService {
    StudentResponse saveStudent(StudentRequest studentRequest);
    void changeStatusOfStudent(Long studentId, Boolean status);
    String updateStudent(StudentUpdateRequest studentRequest);
    StudentResponse updateStudentForManagers(Long userId,
                                            StudentUpdateByManagersRequest studentRequest);
    StudentResponse addLessonProgramToStudent(ChooseLessonProgramWithId chooseLessonProgramWithId);
}
