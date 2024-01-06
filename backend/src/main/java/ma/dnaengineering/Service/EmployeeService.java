package ma.dnaengineering.Service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import ma.dnaengineering.Model.Employee;



@Service
public class EmployeeService {

    public void processFile(MultipartFile file) throws IOException {
        List<Employee> employees = readEmployees(file);
        Map<String, Double> averageSalaries = getAverageSalaryByJobTitle(employees);
    }

    private List<Employee> readEmployees(MultipartFile file) throws IOException {
        List<Employee> employees = new ArrayList<>();
        try (BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {
            String line;
            br.readLine();
            while ((line = br.readLine()) != null) {
                String[] data = line.split(",");
                Employee employee = new Employee();
                employee.setName(data[0]);
                employee.setJobTitle(data[1]);
                employee.setSalary(Double.parseDouble(data[2]));
                employees.add(employee);
            }
        }
        return employees;
    }

    public Map<String, Double> getAverageSalaryByJobTitle(List<Employee> employees) {
        Map<String, Double> averageSalaries = new HashMap<>();
        Map<String, Integer> jobCount = new HashMap<>();

        for (Employee employee : employees) {
            String jobTitle = employee.getJobTitle();
            double salary = employee.getSalary();

            averageSalaries.merge(jobTitle, salary, Double::sum);
            jobCount.merge(jobTitle, 1, Integer::sum);
        }

        averageSalaries.forEach((jobTitle, totalSalary) -> {
            int count = jobCount.getOrDefault(jobTitle, 1);
            averageSalaries.put(jobTitle, totalSalary / (double) count);
        });

        return averageSalaries;
    }
}
