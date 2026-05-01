package com.ucs.service.business.impl;

import com.ucs.payload.mappers.StudentInfoMapper;
import com.ucs.repository.business.StudentInfoRepository;
import com.ucs.service.business.IEducationTermService;
import com.ucs.service.business.ILessonService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.user.IUserService;
import com.ucs.service.user.impl.UserRoleService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentInfoServiceImpl {

    private final StudentInfoRepository studentInfoRepository;
    private final MethodHelper methodHelper;
    private final IUserService userService;
    private final ILessonService lessonService;
    private final IEducationTermService educationTermService;
    private final StudentInfoMapper studentInfoMapper;
    private final PageableHelper pageableHelper;

}
