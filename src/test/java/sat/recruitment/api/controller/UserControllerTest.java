package sat.recruitment.api.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import sat.recruitment.api.exception.UserAlreadyExistsException;
import sat.recruitment.api.model.User;
import sat.recruitment.api.service.UserService;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static sat.recruitment.api.utils.Constants.NORMAL;

@SpringBootTest
@AutoConfigureMockMvc
public class UserControllerTest {
    private User user;

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UserService userService;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setName("Prueba");
        user.setAddress("Dir. prueba");
        user.setMoney(100.0);
        user.setUserType(NORMAL);
        user.setPhone("15654678");
        user.setEmail("prueba@gmail.com");
    }

    @Test
    public void testCreateUserSuccess() throws Exception {
        // Mockear el servicio para que devuelva éxito
        when(userService.createUser(user)).thenReturn(user);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isOk());
    }

    @Test
    public void testCreateUserAlreadyExists() throws Exception {
        // Mockear el servicio para que lance excepción de usuario ya existente
        String errorMessage = "User already exists";
        doThrow(new UserAlreadyExistsException(errorMessage)).when(userService).createUser(user);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest())
                .andExpect(content().string(errorMessage));
    }

    @Test
    public void testCreateUserInternalServerError() throws Exception {
        // Mockear el servicio para que lance excepción genérica
        doThrow(new RuntimeException()).when(userService).createUser(user);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isInternalServerError());
    }

    @Test
    public void testCreateUserMissingName() throws Exception {
        user.setName(null);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserMissingEmail() throws Exception {
        user.setEmail(null);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserMissingAddress() throws Exception {
        user.setAddress(null);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserMissingUserType() throws Exception {
        user.setUserType(null);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserMissingPhone() throws Exception {
        user.setPhone(null);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

    @Test
    public void testCreateUserMissingMoney() throws Exception {
        user.setMoney(null);

        // Enviar petición POST
        mockMvc.perform(post("/api/v1/users")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(user)))
                .andExpect(status().isBadRequest());
    }

}
