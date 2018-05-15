package au.com.adepto.demo.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.reset;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.internal.verification.VerificationModeFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import au.com.adepto.demo.model.Role;
import au.com.adepto.demo.service.RoleService;

@RunWith(SpringRunner.class)
@WebMvcTest(RoleController.class)
public class RoleControllerTest {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private RoleService roleService;

	@Test
	public void givenValidRoleId_whenGET_willReturnJSONRole() throws Exception {

		Role role = new Role(4L, "Manager");

		given(roleService.findById(anyLong())).willReturn(Optional.of(role));

		mvc.perform(get("/api/roles/{id}", 4).accept(MediaType.APPLICATION_JSON)).andExpect(status().isOk())
				.andExpect(jsonPath("$.id", is((int) role.getId())))
				.andExpect(jsonPath("$.description", is(role.getDescription())));

		verify(roleService, VerificationModeFactory.times(1)).findById(role.getId());
		verifyNoMoreInteractions(roleService);

		reset(roleService);

	}

}