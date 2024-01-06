package ma.dnaengineering.Controller;


import org.springframework.http.HttpStatus;

import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import ma.dnaengineering.Model.Employee;
import ma.dnaengineering.Service.EmployeeService;

import java.io.IOException;
import java.net.http.HttpHeaders;
import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/api")
public class EmployeeController {

    private final EmployeeService employeeService;

    public EmployeeController(EmployeeService employeeService) {
        this.employeeService = employeeService;
    }
    
    @GetMapping("/home")
    public String myfunc() {
        return "index";
    }


    @PostMapping("/upload")
    public ResponseEntity<String> handleFileUpload(@RequestParam("file") MultipartFile file) {
        try {
            employeeService.processFile(file);
            return ResponseEntity.ok("File uploaded and processed successfully!");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body("Error processing the file: " + e.getMessage());
        }
    }

    @GetMapping("/average-salaries")
    public ResponseEntity<Map<String, Double>> getAverageSalaries(List<Employee> employees) {
        Map<String, Double> averageSalaries = employeeService.getAverageSalaryByJobTitle(employees);
        return ResponseEntity.ok(averageSalaries);
    }
}
