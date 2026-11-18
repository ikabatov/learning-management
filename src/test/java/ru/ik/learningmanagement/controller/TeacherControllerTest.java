package ru.ik.learningmanagement.controller;

import com.jayway.jsonpath.JsonPath;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.webmvc.test.autoconfigure.AutoConfigureMockMvc;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.ik.learningmanagement.TestcontainersConfiguration;
import ru.ik.learningmanagement.entity.Teacher;
import ru.ik.learningmanagement.repository.TeacherRepository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
@Import(TestcontainersConfiguration.class)
class TeacherControllerTest {

    private static final String URL = "/api/v1/teachers";
    private static final long NON_EXISTENT_ID = Long.MAX_VALUE;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private TeacherRepository teacherRepository;

    private Teacher ivan;

    @BeforeEach
    void setUp() {
        ivan = teacherRepository.save(createTeacher("Ivan", "Petrov"));
        teacherRepository.save(createTeacher("Anna", "Smirnova"));
    }

    @AfterEach
    void tearDown() {
        teacherRepository.deleteAll();
    }

    @Test
    void getTeacherById_whenExists_returnsTeacher() throws Exception {
        mockMvc.perform(get(URL + "/{id}", ivan.getId()))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ivan.getId()))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Petrov"));
    }

    @Test
    void getTeacherById_whenNotExists_returns404() throws Exception {
        mockMvc.perform(get(URL + "/{id}", NON_EXISTENT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Учитель с id " + NON_EXISTENT_ID + " не найден"));
    }

    @Test
    void createTeacher_whenValid_returns201AndSavesToDb() throws Exception {
        String requestBody = """
                {"firstName": "Oleg", "lastName": "Sidorov"}
                """;

        String responseBody = mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").isNumber())
                .andExpect(jsonPath("$.firstName").value("Oleg"))
                .andExpect(jsonPath("$.lastName").value("Sidorov"))
                .andReturn()
                .getResponse()
                .getContentAsString();

        Long createdId = JsonPath.parse(responseBody).read("$.id", Long.class);
        Teacher saved = teacherRepository.findById(createdId).orElseThrow();
        assertThat(saved.getFirstName()).isEqualTo("Oleg");
        assertThat(saved.getLastName()).isEqualTo("Sidorov");
        assertThat(teacherRepository.count()).isEqualTo(3);
    }

    @Test
    void createTeacher_whenFirstNameBlank_returns400AndDoesNotSave() throws Exception {
        String requestBody = """
                {"firstName": "", "lastName": "Sidorov"}
                """;

        mockMvc.perform(post(URL)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        assertThat(teacherRepository.count()).isEqualTo(2);
    }

    @Test
    void updateTeacher_whenValid_returnsUpdatedTeacher() throws Exception {
        String requestBody = """
                {"firstName": "Ivan", "lastName": "Ivanov"}
                """;

        mockMvc.perform(put(URL + "/{id}", ivan.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(ivan.getId()))
                .andExpect(jsonPath("$.firstName").value("Ivan"))
                .andExpect(jsonPath("$.lastName").value("Ivanov"));

        Teacher updated = teacherRepository.findById(ivan.getId()).orElseThrow();
        assertThat(updated.getFirstName()).isEqualTo("Ivan");
        assertThat(updated.getLastName()).isEqualTo("Ivanov");
    }

    @Test
    void updateTeacher_whenNotExists_returns404() throws Exception {
        String requestBody = """
                {"firstName": "Ivan", "lastName": "Ivanov"}
                """;

        mockMvc.perform(put(URL + "/{id}", NON_EXISTENT_ID)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Учитель с id " + NON_EXISTENT_ID + " не найден"));
    }

    @Test
    void updateTeacher_whenLastNameBlank_returns400AndDoesNotUpdate() throws Exception {
        String requestBody = """
                {"firstName": "Ivan", "lastName": ""}
                """;

        mockMvc.perform(put(URL + "/{id}", ivan.getId())
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(requestBody))
                .andExpect(status().isBadRequest());

        Teacher notChanged = teacherRepository.findById(ivan.getId()).orElseThrow();
        assertThat(notChanged.getLastName()).isEqualTo("Petrov");
    }

    @Test
    void deleteTeacher_whenExists_returns204AndRemovesFromDb() throws Exception {
        mockMvc.perform(delete(URL + "/{id}", ivan.getId()))
                .andExpect(status().isNoContent());

        assertThat(teacherRepository.existsById(ivan.getId())).isFalse();
        assertThat(teacherRepository.count()).isEqualTo(1);
    }

    @Test
    void deleteTeacher_whenNotExists_returns404() throws Exception {
        mockMvc.perform(delete(URL + "/{id}", NON_EXISTENT_ID))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$.message").value("Учитель с id " + NON_EXISTENT_ID + " не найден"));

        assertThat(teacherRepository.count()).isEqualTo(2);
    }

    private Teacher createTeacher(String firstName, String lastName) {
        Teacher teacher = new Teacher();
        teacher.setFirstName(firstName);
        teacher.setLastName(lastName);
        return teacher;
    }
}
