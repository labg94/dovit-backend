package com.dovit.backend.services;

import com.dovit.backend.domain.Role;
import com.dovit.backend.exceptions.ResourceNotFoundException;
import com.dovit.backend.repositories.RoleRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static com.dovit.backend.util.DomainBuilderUtil.role;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

/**
 * @author Ramón París
 * @since 12-04-20
 */
@ExtendWith(SpringExtension.class)
class RoleServiceImplTest {

  @InjectMocks private RoleServiceImpl roleService;
  @Mock private RoleRepository roleRepository;

  @Test
  void findById_OK() {
    when(roleRepository.findById(anyLong())).thenReturn(Optional.of(role));
    Role response = roleService.findById(1L);
    assertNotNull(response);
    assertEquals(response, role);
  }

  @Test
  void findById_EXCEPTION() {
    when(roleRepository.findById(anyLong())).thenReturn(Optional.empty());
    assertThrows(ResourceNotFoundException.class, () -> roleService.findById(1L));
  }
}
