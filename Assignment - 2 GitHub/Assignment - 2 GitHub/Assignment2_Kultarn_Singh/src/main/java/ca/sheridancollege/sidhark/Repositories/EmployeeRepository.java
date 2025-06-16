package ca.sheridancollege.sidhark.Repositories;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Repository;
import ca.sheridancollege.sidhark.Beans.Employee;
import lombok.AllArgsConstructor;

@Repository
@AllArgsConstructor
public class EmployeeRepository {

    private final NamedParameterJdbcTemplate jdbc;
    private final StoreRepository emp;

    private static final String EMPLOYEE_INSERT = 
        "INSERT INTO AnimeEmployee (StoreName, Name, ShiftDate, ShiftStartTime, ShiftEndTime, Role, HourlyRate) VALUES (:storeName, :name, :shiftDate, :shiftStartTime, :shiftEndTime, :role, :hourlyRate)";

    private static final String EMPLOYEE_UPDATE = 
        "UPDATE AnimeEmployee SET StoreName = :storeName, Name = :name, ShiftDate = :shiftDate, ShiftStartTime = :shiftStartTime, ShiftEndTime = :shiftEndTime, Role = :role, HourlyRate = :hourlyRate WHERE EmployeeId = :employeeId";

    private static final String EMPLOYEE_DELETE = "DELETE FROM AnimeEmployee WHERE EmployeeId = :employeeId";
    private static final String EMPLOYEE_SELECT_BY_ID = "SELECT * FROM AnimeEmployee WHERE EmployeeId = :employeeId";
    private static final String EMPLOYEE_SELECT_ALL = "SELECT * FROM AnimeEmployee";

    public void addTestEmployee() {
        Random rand = new Random();
        ArrayList<String> list = emp.getStoreNames();
        Employee employee = Employee.builder()
                .employeeId(0)
                .storeName(list.get(rand.nextInt(list.size())))
                .name("Kultarn")
                .shiftDate(LocalDate.parse("2025-06-12"))
                .shiftStartTime(LocalTime.parse("10:00"))
                .shiftEndTime(LocalTime.parse("18:00"))
                .role("Inventory Manager")
                .hourlyRate(19.75)
                .build();

        addEmployee(employee);
    }
    
    public void deleteEmployeesByStoreName(String storeName) {
        String sql = "DELETE FROM AnimeEmployee WHERE StoreName = :storeName";
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("storeName", storeName);
        jdbc.update(sql, params);
    }
    
    private MapSqlParameterSource mapEmployee(Employee employee, boolean includeId) {
        MapSqlParameterSource params = new MapSqlParameterSource()
            .addValue("storeName", employee.getStoreName())
            .addValue("name", employee.getName())
            .addValue("shiftDate", employee.getShiftDate())
            .addValue("shiftStartTime", employee.getShiftStartTime())
            .addValue("shiftEndTime", employee.getShiftEndTime())
            .addValue("role", employee.getRole())
            .addValue("hourlyRate", employee.getHourlyRate());

        if (includeId) {
            params.addValue("employeeId", employee.getEmployeeId());
        }
        return params;
    }

    public void addEmployee(Employee employee) {
        jdbc.update(EMPLOYEE_INSERT, mapEmployee(employee, false));
    }

    public void updateEmployee(Employee employee) {
        jdbc.update(EMPLOYEE_UPDATE, mapEmployee(employee, true));
    }

    public ArrayList<Employee> getAllEmployees() {
        List<Employee> employees = jdbc.query(
            EMPLOYEE_SELECT_ALL,
            new MapSqlParameterSource(),
            new BeanPropertyRowMapper<>(Employee.class)
        );
        return new ArrayList<>(employees);
    }

    public void deleteEmployeeById(int employeeId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("employeeId", employeeId);
        jdbc.update(EMPLOYEE_DELETE, params);
    }

    public Employee getEmployeeById(int employeeId) {
        MapSqlParameterSource params = new MapSqlParameterSource().addValue("employeeId", employeeId);
        return jdbc.queryForObject(EMPLOYEE_SELECT_BY_ID, params, new BeanPropertyRowMapper<>(Employee.class));
    }
}