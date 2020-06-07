package com.dovit.backend.controllers;

import com.dovit.backend.domain.Member;
import com.dovit.backend.payloads.requests.MemberRequest;
import com.dovit.backend.payloads.responses.MemberResponseDetail;
import com.dovit.backend.services.MemberServiceImpl;
import com.dovit.backend.util.TestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.ArrayList;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;

/**
 * @author Ramón París
 * @since 30-03-20
 */
@ExtendWith(SpringExtension.class)
class MemberControllerTest {

  @InjectMocks MemberController memberController;
  @Mock MemberServiceImpl memberService;
  private MockMvc mockMvc;

  private MemberRequest memberRequest =
      MemberRequest.builder().companyId(1L).name("Ramón").lastName("París").build();

  private Member member = Member.builder().id(1L).build();

  @BeforeEach
  void setUp() {
    this.mockMvc = MockMvcBuilders.standaloneSetup(memberController).build();
  }

  @Test
  void findMemberByCompanyIdAndMemberId() {
    Mockito.when(memberService.findById(anyLong())).thenReturn(new MemberResponseDetail());
    TestUtils.testGetRequest(mockMvc, "/member/1");
  }

  @Test
  void findAllByCompanyId() {
    Mockito.when(memberService.findAllByCompanyId(anyLong())).thenReturn(new ArrayList<>());
    TestUtils.testGetRequest(mockMvc, "/company/1/members");
  }

  @Test
  void addMember() {
    Mockito.when(memberService.save(any(MemberRequest.class))).thenReturn(member);
    TestUtils.testPostRequest(mockMvc, "/member", this.memberRequest);
  }

  @Test
  void updateMember() {
    Mockito.when(memberService.update(any(MemberRequest.class))).thenReturn(member);
    TestUtils.testPutRequest(mockMvc, "/member", this.memberRequest);
  }
}
