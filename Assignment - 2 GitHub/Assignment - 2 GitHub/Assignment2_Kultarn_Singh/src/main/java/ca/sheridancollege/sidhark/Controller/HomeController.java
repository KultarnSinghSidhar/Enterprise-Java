package ca.sheridancollege.sidhark.Controller;

import java.util.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import ca.sheridancollege.sidhark.Beans.AnimeStore;
import ca.sheridancollege.sidhark.Beans.Employee;
import ca.sheridancollege.sidhark.Repositories.StoreRepository;
import ca.sheridancollege.sidhark.Repositories.EmployeeRepository;

@Controller
@AllArgsConstructor
public class HomeController {

    private final StoreRepository storeRepo;
    private final EmployeeRepository employeeRepo;

    @GetMapping("/")
    public String home() {
        return "Homepage";
    }

    @GetMapping("/test/employee")
    public String addTestEmployee() {
        employeeRepo.addTestEmployee();
        return "redirect:/view-all";
    }

    @GetMapping("/test/store")
    public String addTestStore() {
        storeRepo.addTestStore();
        return "redirect:/view-all";
    }

    @GetMapping("/add-store-form")
    public String showAddStoreForm(Model model) {
        model.addAttribute("animeStore", new AnimeStore());
        return "AddStore";
    }
    
    @GetMapping("/stores")
    public String viewStores(Model Model) {
    		Model.addAttribute("stores", storeRepo.getStores());
        return "Store";
    }

    @GetMapping("/add-employee-form")
    public String showAddEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("storeList", storeRepo.getStoreNames());
        return "AddEmployee";
    }
    
    @GetMapping("/employees")
    public String viewEmployees(Model model) {
    		model.addAttribute("employees", employeeRepo.getAllEmployees());
        return "Employees";
    }

    @PostMapping("/add-store-data")
    public String addStore(@ModelAttribute AnimeStore animeStore) {
        storeRepo.addStore(animeStore);
        return "redirect:/view-all";
    }

    @PostMapping("/add-employee-data")
    public String addEmployee(@ModelAttribute Employee employee) {
        employeeRepo.addEmployee(employee);
        return "redirect:/view-all";
    }

    @GetMapping("/view-all")
    public String viewAll(Model model) {
        Map<AnimeStore, List<Employee>> data = mapStoreToEmployees();
        model.addAttribute("Data", data);
        return "View-All";
    }

    @GetMapping("/edit-employee/{id}")
    public String editEmployeeForm(@PathVariable int id, Model model) {
        Employee employee = employeeRepo.getEmployeeById(id);
        model.addAttribute("employee", employee);
        return "EditEmployee";
    }

    @PostMapping("/edit-employee/{id}")
    public String updateEmployee(@PathVariable int id, @ModelAttribute Employee employee) {
        employee.setEmployeeId(id);
        employeeRepo.updateEmployee(employee);
        return "redirect:/view-all";
    }

    @GetMapping("/delete-employee/{id}")
    public String deleteEmployee(@PathVariable int id) {
        employeeRepo.deleteEmployeeById(id);
        return "redirect:/view-all";
    }

    @GetMapping("/edit-store/{id}")
    public String editStoreForm(@PathVariable int id, Model model) {
        AnimeStore store = storeRepo.getStoreById(id);
        model.addAttribute("animeStore", store);
        return "EditStore";
    }

    @PostMapping("/edit-store/{id}")
    public String updateStore(@PathVariable int id, @ModelAttribute AnimeStore animeStore) {
        storeRepo.updateStore(animeStore);
        return "redirect:/view-all";
    }

    @GetMapping("/delete-store/{id}")
    public String deleteStore(@PathVariable int id) {
        AnimeStore store = storeRepo.getStoreById(id);
        employeeRepo.deleteEmployeesByStoreName(store.getName());
        storeRepo.deleteStoreById(id);
        return "redirect:/view-all";
    }

    private Map<AnimeStore, List<Employee>> mapStoreToEmployees() {
        List<AnimeStore> stores = storeRepo.getStores();
        List<Employee> employees = employeeRepo.getAllEmployees();
        Map<AnimeStore, List<Employee>> result = new HashMap<>();

        for (AnimeStore store : stores) {
            List<Employee> match = new ArrayList<>();
            for (Employee e : employees) {
                if (e.getStoreName() != null && e.getStoreName().equals(store.getName())) {
                    match.add(e);
                }
            }
            result.put(store, match);
        }

        return result;
    }
}