package com.ucs.service.business.impl;

import com.ucs.contactmessage.messages.Messages;
import com.ucs.entity.concretes.business.Meet;
import com.ucs.entity.concretes.user.User;
import com.ucs.entity.enums.RoleType;
import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.mappers.MeetMapper;
import com.ucs.payload.request.business.MeetRequest;
import com.ucs.payload.response.business.MeetResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.repository.business.MeetRepository;
import com.ucs.service.business.IMeetService;
import com.ucs.service.helper.MethodHelper;
import com.ucs.service.helper.PageableHelper;
import com.ucs.service.user.IUserService;
import com.ucs.service.validator.DateTimeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class MeetServiceImpl implements IMeetService {

    private final MeetRepository meetRepository;
    private final IUserService userService;
    private final MethodHelper methodHelper;
    private final DateTimeValidator dateTimeValidator;
    private final MeetMapper meetMapper;
    private final PageableHelper pageableHelper;

    @Transactional
    public MeetResponse saveMeet(UserResponse authenticatedUser, MeetRequest meetRequest) {
        User advisorTeacher = methodHelper.getUserByUsername(authenticatedUser.getUsername());
        methodHelper.checkAdvisor(advisorTeacher);

        dateTimeValidator.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());

        methodHelper.checkMeetConflict(
                advisorTeacher.getId(),
                meetRequest.getDate(),
                meetRequest.getStartTime(),
                meetRequest.getStopTime());

        for (Long studentId : meetRequest.getStudentsIds()) {
            User student = methodHelper.getUserById(studentId);
            methodHelper.checkRole(student, RoleType.STUDENT);

            methodHelper.checkMeetConflict(
                    studentId,
                    meetRequest.getDate(),
                    meetRequest.getStartTime(),
                    meetRequest.getStopTime()
            );
        }

        List<User> students = methodHelper.getStudentById(new ArrayList<>(meetRequest.getStudentsIds()));

        Meet meet = meetMapper.requestToMeet(meetRequest);
        meet.setStudentList(new HashSet<>(students));
        meet.setAdvisoryTeacher(advisorTeacher);
        Meet savedMeet = meetRepository.save(meet);

        return meetMapper.meetToMeetResponse(savedMeet);
    }

    @Transactional
    public String delete(Long meetId, UserResponse authenticatedUser) {

        Meet meet = methodHelper.isMeetExistById(meetId);
        methodHelper.isTeacherControl(meet, authenticatedUser.getUsername());

        meetRepository.delete(meet);

        return Messages.getMessage(SuccessMessageType.MEET_DELETED.getMessage());
    }

    public List<MeetResponse> getAllMeetByStudent(UserResponse authenticatedUser) {
        User student = methodHelper.getUserByUsername(authenticatedUser.getUsername());
        methodHelper.checkRole(student, RoleType.STUDENT);
        return meetRepository.findByStudentList_Id(student.getId())
                .stream()
                .map(meetMapper::meetToMeetResponse)
                .collect(Collectors.toList());
    }

    public Page<MeetResponse> getAllMeetByTeacher(UserResponse authenticateduser, int page, int size) {
        User advisoryTeacher = methodHelper.getUserByUsername(authenticateduser.getUsername());
        methodHelper.checkAdvisor(advisoryTeacher);
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);
        return meetRepository.findByAdvisoryTeacher_Id(advisoryTeacher.getId(), pageable)
                .map(meetMapper::meetToMeetResponse);
    }

    public MeetResponse updatemeet(MeetRequest meetRequest, Long meetId, UserResponse authenticatedUser) {

        Meet meet = methodHelper.isMeetExistById(meetId);
        methodHelper.isTeacherControl(meet, authenticatedUser.getUsername());
        dateTimeValidator.checkTimeWithException(meetRequest.getStartTime(), meetRequest.getStopTime());

        if (!(meet.getDate().equals(meetRequest.getDate()) &&
                meet.getStartTime().equals(meetRequest.getStartTime()) &&
                meet.getStopTime().equals(meetRequest.getStopTime()))) {

            for (Long studentId : meetRequest.getStudentsIds()) {
                methodHelper.checkMeetConflict(
                        studentId,
                        meetRequest.getDate(),
                        meetRequest.getStartTime(),
                        meetRequest.getStopTime()
                );
            }

            methodHelper.checkMeetConflict(
                    meet.getAdvisoryTeacher().getId(),
                    meetRequest.getDate(),
                    meetRequest.getStartTime(),
                    meetRequest.getStopTime()
            );
        }
        List<User> students = methodHelper.getStudentById(new ArrayList<>(meetRequest.getStudentsIds()));

        Meet updatedMeet = meetMapper.updatetoMeetFromRequest(meetRequest, meet);

        updatedMeet.setAdvisoryTeacher(meet.getAdvisoryTeacher());
        updatedMeet.setStudentList(new HashSet<>(students));

        Meet savedMeet = meetRepository.save(updatedMeet);

        return meetMapper.meetToMeetResponse(updatedMeet);
    }

    public Page<MeetResponse> getAll(UserResponse authenticatedUser, int page, int size) {

        User user = methodHelper.getUserByUsername(authenticatedUser.getUsername());
        methodHelper.checkAdvisor(user);
        Pageable pageable = pageableHelper.getPageableWithProperties(page, size);
        return meetRepository.findAllMeetByAdvisorTeacherId(user.getId(), pageable)
                .map(meetMapper::meetToMeetResponse);
    }

    public MeetResponse getMeetById(Long meetId) {
        return meetMapper.meetToMeetResponse(methodHelper.isMeetExistById(meetId));
    }
}
