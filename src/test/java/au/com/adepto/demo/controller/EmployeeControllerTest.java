//package au.com.adepto.demo.controller;
//
//import org.junit.runner.RunWith;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
//import org.springframework.boot.test.mock.mockito.MockBean;
//import org.springframework.test.context.junit4.SpringRunner;
//import org.springframework.test.web.servlet.MockMvc;
//
//import au.com.adepto.demo.service.EmployeeService;
//
//@RunWith(SpringRunner.class)
//@WebMvcTest(EmployeeController.class)
//public class EmployeeControllerTest {
// 
//    @Autowired
//    private MockMvc mvc;
// 
//    @MockBean
//    private EmployeeService employeService;
// 
//    @Test
//    public void givenEmployees_whenGetEmployees_thenReturnJsonArray()
//      throws Exception {
//         
//        Employee alex = new Employee("alex");
//     
//        List<Employee> allEmployees = Arrays.asList(alex);
//     
//        given(employeService.findAll()).willReturn(allEmployees);
//     
//        mvc.perform(get("/api/employees")
//          .contentType(MediaType.APPLICATION_JSON))
//          .andExpect(status().isOk())
//          .andExpect(jsonPath("$", hasSize(1)))
//          .andExpect(jsonPath("$[0].name", is(alex.getName())));
//    }
//    
//}