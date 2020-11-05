package liveproject.m2k8s.web;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.view;
import static org.springframework.test.web.servlet.setup.MockMvcBuilders.standaloneSetup;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = {HomeController.class})
public class HomeControllerTest {
    @Autowired
    private HomeController homeController;

    @Test
    public void testHomePage() throws Exception {
        final MockMvc mockMvc = standaloneSetup(homeController).build();
        mockMvc.perform(get("/"))
                .andExpect(view().name("home"));
    }
}
