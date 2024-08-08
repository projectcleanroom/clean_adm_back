package com.clean.cleanroom.members.controller;

import com.clean.cleanroom.members.dto.MembersProfileResponseDto;
import com.clean.cleanroom.members.dto.MembersRequestDto;
import com.clean.cleanroom.members.dto.MembersSignupResponseDto;
import com.clean.cleanroom.members.service.MembersService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/members")
public class MembersController {
    private final MembersService membersService;

    public MembersController(MembersService membersService) {
        this.membersService = membersService;
    }

//    @PostMapping("/signup")
//    public ResponseEntity<MembersSignupResponseDto> signup(@RequestBody @Valid MembersRequestDto requestDto) {
//        MembersSignupResponseDto membersSignupResponseDto = membersService.signup(requestDto);
//        return new ResponseEntity<>(membersSignupResponseDto, HttpStatus.CREATED);
//    }

//    @PutMapping("/profile")
//    public ResponseEntity<MembersProfileResponseDto> profile(@RequestParam Long id, @RequestBody @Valid MembersRequestDto requestDto) {
//        MembersProfileResponseDto membersProfileResponseDto = membersService.profile(id, requestDto);
//        return new ResponseEntity<>(membersProfileResponseDto, HttpStatus.OK);
//    }
}
