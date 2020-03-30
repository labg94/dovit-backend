package com.dovit.backend.controllers;

import com.dovit.backend.domain.Member;
import com.dovit.backend.model.requests.MemberRequest;
import com.dovit.backend.model.responses.ApiResponse;
import com.dovit.backend.model.responses.MemberResponse;
import com.dovit.backend.services.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
@Secured({"ROLE_ADMIN", "ROLE_CLIENT"})
@CrossOrigin(origins = "*")
public class MemberController {

    private final MemberService memberService;

    @GetMapping("/member/{memberId}")
    public MemberResponse findMemberByCompanyIdAndMemberId(@PathVariable Long memberId) {
        return memberService.findById(memberId);
    }

    @GetMapping("/company/{companyId}/members")
    public List<MemberResponse> findAllByCompanyId(@PathVariable Long companyId) {
        return memberService.findAllByCompanyId(companyId);
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
