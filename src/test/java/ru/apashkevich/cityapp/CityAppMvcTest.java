package ru.apashkevich.cityapp;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.apashkevich.cityapp.dao.mapper.CityRowMapper;
import ru.apashkevich.cityapp.model.City;

import java.util.List;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.hasSize;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc
class CityAppMvcTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    NamedParameterJdbcTemplate template;

    @Autowired
    CityRowMapper cityRowMapper;

    @Test
    @WithMockUser(username = "andrei", roles={"ALLOW_VIEW"})
    public void shouldCorrectlyReturnListOfCities() throws Exception {
        mockMvc.perform(get("/cities").contentType(MediaType.APPLICATION_JSON)
                        .param("limit", "2")
                        .param("offset", "0"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1000))
                .andExpect(jsonPath("$.cities[0].id").value(1))
                .andExpect(jsonPath("$.cities[0].name").value("Tokyo"))
                .andExpect(jsonPath("$.cities[1].id").value(2))
                .andExpect(jsonPath("$.cities[1].name").value("Jakarta"))
                .andExpect(jsonPath("$.cities[2].id").doesNotExist())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "andrei", roles={"ALLOW_VIEW"})
    public void shouldCorrectlyReturnListOfCitiesWithTextSearch() throws Exception {
        mockMvc.perform(get("/cities").contentType(MediaType.APPLICATION_JSON)
                        .param("limit", "10")
                        .param("offset", "0")
                        .param("searchText", "Tok"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.total").value(1))
                .andExpect(jsonPath("$.cities[0].id").value(1))
                .andExpect(jsonPath("$.cities[0].name").value("Tokyo"))
                .andExpect(jsonPath("$.cities[1].id").doesNotExist())
                .andReturn();
    }

    @Test
    @WithMockUser(username = "andrei", roles={"ALLOW_EDIT"})
    public void shouldCorrectlyUpdateCity() throws Exception {
        String body = "{\n" +
                "  \"id\": 31,\n" +
                "  \"name\": \"Paris12\",\n" +
                "  \"photo\": \"Paris12\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/cities/31").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().isOk())
                .andReturn();

        String sql = "SELECT id, name, photo FROM city.city WHERE id = 31";
        List<City> cities = template.query(sql, cityRowMapper);
        assertThat(cities, hasSize(1));
        assertThat(cities.get(0).getName(), is("Paris12"));
        assertThat(cities.get(0).getPhoto(), is("Paris12"));
    }

    @Test
    @WithMockUser(username = "andrei", roles={"ALLOW_EDIT"})
    public void shouldNotUpdateCityIfCityNotFound() throws Exception {
        String body = "{\n" +
                "  \"id\": 31,\n" +
                "  \"name\": \"Paris12\",\n" +
                "  \"photo\": \"Paris12\"\n" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/cities/10000000").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(404))
                .andReturn();
    }

    @Test
    @WithMockUser(username = "andrei", roles={"ALLOW_EDIT"})
    public void shouldNotUpdateIifPassedIncorrectParameters() throws Exception {
        String body = "{\n" +
                "  \"id\": 31,\n" +
                "  \"name\": null" +
                "  \"photo\": null" +
                "}";
        mockMvc.perform(MockMvcRequestBuilders.put("/cities/31").contentType(MediaType.APPLICATION_JSON)
                        .content(body))
                .andExpect(status().is(400))
                .andReturn();
    }
}
