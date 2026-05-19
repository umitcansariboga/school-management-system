package com.ucs.controller.user;

import com.ucs.messages.SuccessMessageType;
import com.ucs.payload.request.updateRequest.UserUpdateByAdminRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByAssistantRequest;
import com.ucs.payload.request.updateRequest.UserUpdateByManagerRequest;
import com.ucs.payload.request.user.UserRequest;
import com.ucs.payload.request.user.UserRequestWithoutPassword;
import com.ucs.payload.response.ResponseMessage;
import com.ucs.payload.response.abstracts.BaseUserResponse;
import com.ucs.payload.response.user.UserResponse;
import com.ucs.service.user.IUserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping("/{userRole}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<UserResponse>> create(
            @RequestBody @Valid UserRequest userRequest,
            @PathVariable(value = "userRole") String userRole
    ) {
        return ResponseEntity.ok(ResponseMessage.success(userService.saveUser(userRequest, userRole),
                SuccessMessageType.USER_SAVED));
    }

    @GetMapping("/{userRole}/page")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<Page<UserResponse>> getByPage(
            @PathVariable(value = "userRole") String userRole,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size,
            @RequestParam(value = "sort", defaultValue = "name") String sort,
            @RequestParam(value = "type", defaultValue = "desc") String type
    ) {
        return ResponseEntity.ok(userService.getUsersByPage(page, size, sort, type, userRole));
    }

    @GetMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<ResponseMessage<BaseUserResponse>> getById(
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(ResponseMessage.success(userService.getUserById(userId),
                SuccessMessageType.USER_FOUND));
    }

    @DeleteMapping("/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<String> deleteById(
            @PathVariable(value = "userId") Long userId
    ) {
        return ResponseEntity.ok(userService.deleteUserById(userId));
    }

    @PutMapping("/admin/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN')")
    public ResponseEntity<ResponseMessage<BaseUserResponse>> updateUserByAdmin(
            @RequestBody @Valid UserUpdateByAdminRequest adminRequest,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(ResponseMessage.success(userService.updateUserByAdmin(adminRequest, userId),
                SuccessMessageType.USER_UPDATED));
    }

    @PutMapping("/manager/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER')")
    public ResponseEntity<ResponseMessage<BaseUserResponse>> updateUserByManager(
            @RequestBody @Valid UserUpdateByManagerRequest managerRequest,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(ResponseMessage.success(userService.updateUserByManager(managerRequest, userId),
                SuccessMessageType.USER_UPDATED));
    }

    @PutMapping("/assistant/{userId}")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<ResponseMessage<BaseUserResponse>> updateUserByAssistant(
            @RequestBody @Valid UserUpdateByAssistantRequest assistantRequest,
            @PathVariable Long userId
    ) {
        return ResponseEntity.ok(ResponseMessage.success(userService.updateUserByAssistant(assistantRequest, userId),
                SuccessMessageType.USER_UPDATED));
    }

    @PatchMapping("/me")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER','TEACHER','STUDENT')")
    public ResponseEntity<ResponseMessage<BaseUserResponse>> updateSelf(
            @RequestBody @Valid UserRequestWithoutPassword userRequest
    ) {
        return ResponseEntity.ok(ResponseMessage.success(userService.updateUserForUsers(userRequest),
                SuccessMessageType.USER_PROFILE_UPDATED));
    }

    @GetMapping("/search")
    @PreAuthorize("hasAnyAuthority('ADMIN','MANAGER','ASSISTANT_MANAGER')")
    public ResponseEntity<Page<UserResponse>> searchByName(
            @RequestParam(name = "name") String userName,
            @RequestParam(value = "page", defaultValue = "0") int page,
            @RequestParam(value = "size", defaultValue = "10") int size
    ) {
        return ResponseEntity.ok(userService.getUserByName(page, size, userName));
    }
}
