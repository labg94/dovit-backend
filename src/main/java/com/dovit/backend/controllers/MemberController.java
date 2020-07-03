package com.dovit.backend.controllers;

import com.dovit.backend.annotations.IsAnyAdmin;
import com.dovit.backend.annotations.IsAuthenticated;
import com.dovit.backend.domain.Member;
import com.dovit.backend.payloads.requests.MemberRequest;
import com.dovit.backend.payloads.responses.ApiResponse;
import com.dovit.backend.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@IsAnyAdmin
@CrossOrigin(origins = "*")
public class MemberController {

  private final MemberService memberService;

  @IsAuthenticated
  @GetMapping("/member/{memberId}")
  public ResponseEntity<?> findMemberByMemberId(@PathVariable Long memberId) {
    return ResponseEntity.ok(memberService.findById(memberId));
  }

  @IsAuthenticated
  @GetMapping("/company/{companyId}/members")
  public ResponseEntity<?> findAllByCompanyId(@PathVariable Long companyId) {
    return ResponseEntity.ok(memberService.findAllByCompanyId(companyId));
  }

  @PostMapping("/member")
  public ResponseEntity<?> addMember(@Valid @RequestBody MemberRequest member) {
    Member response = memberService.save(member);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Member created successfully"));
  }

  @PutMapping("/member")
  public ResponseEntity<?> updateMember(@Valid @RequestBody MemberRequest request) {
    Member response = memberService.update(request);

    URI location =
        ServletUriComponentsBuilder.fromCurrentRequest()
            .path("/{id}")
            .buildAndExpand(response.getId())
            .toUri();

    return ResponseEntity.created(location)
        .body(new ApiResponse(true, "Member updated successfully"));
  }
}
