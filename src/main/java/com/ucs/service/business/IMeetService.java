package com.ucs.service.business;

import com.ucs.payload.request.business.MeetRequest;
import com.ucs.payload.response.business.MeetResponse;
import org.springframework.data.domain.Page;

import java.util.List;

public interface IMeetService {
    MeetResponse saveMeet(MeetRequest meetRequest);

    String delete(Long meetId);

    List<MeetResponse> getAllMeetByStudent();

    Page<MeetResponse> getAllMeetByTeacher(int page, int size);

    MeetResponse updatemeet(MeetRequest meetRequest, Long meetId);

    Page<MeetResponse> getAllMeetByPage(int page, int size);

    MeetResponse getMeetById(Long meetId);
}
