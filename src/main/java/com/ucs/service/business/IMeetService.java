package com.ucs.service.business;

import com.ucs.payload.request.business.MeetRequest;
import com.ucs.payload.response.business.MeetResponse;
import com.ucs.payload.response.user.UserResponse;

public interface IMeetService {
    MeetResponse saveMeet(UserResponse authenticatedUser, MeetRequest meetRequest);
    String delete(Long meetId, UserResponse authenticatedUser);
}
