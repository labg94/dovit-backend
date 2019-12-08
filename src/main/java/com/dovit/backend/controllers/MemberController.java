package com.dovit.backend.controllers;

import com.dovit.backend.domain.Member;
import com.dovit.backend.model.requests.MemberRequest;
import com.dovit.backend.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN","ROLE_CLIENT"})
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberService memberService;

    @PostMapping("member")
    public ResponseEntity<?> addMember(@RequestBody MemberRequest member){
        return ResponseEntity.ok(memberService.agregar(member));
    }


}
