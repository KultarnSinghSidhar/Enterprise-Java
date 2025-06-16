package ca.sheridancollege.sidhark.Controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import ca.sheridancollege.sidhark.Class.Ticket;
import ca.sheridancollege.sidhark.Database.Database;
import org.springframework.ui.Model;

@Controller
public class HomeControllers {

    private ArrayList<Ticket> ticketList = Database.ticketList;

    @GetMapping("/")
    public String home() {
        return "HomePage";
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("ticket", new Ticket());
        model.addAttribute("ticketCategoryList", List.of("General", "VIP", "Staff"));
        model.addAttribute("preferredSeatAreaList", List.of("Front", "Middle", "Back"));
        return "Register";
    }

    @PostMapping("/add-tickets")
    public String addTickets(@ModelAttribute Ticket ticket) {
        ticketList.add(ticket);
        System.out.println("Added ticket: " + ticket);
        System.out.println("Total tickets: " + ticketList.size());
        return "redirect:/view-tickets";
    }

    @GetMapping("/view-tickets")
    public String viewTickets(Model model) {
    	
    	if(ticketList.size() == 0) {
            ticketList.add(new Ticket("Alice Johnson", "alice.johnson@example.com", "2025-08-15", 75.00, "Vegetarian meal", "VIP", "Front"));
            ticketList.add(new Ticket("Bob Smith", "bob.smith@example.com", "2025-08-15", 50.00, "", "General", "Middle"));
            ticketList.add(new Ticket("Carol Davis", "carol.davis@example.com", "2025-08-16", 100.00, "Wheelchair access needed", "Staff", "Back"));
            ticketList.add(new Ticket("David Lee", "david.lee@example.com", "2025-08-17", 60.00, "Gluten-free snacks", "General", "Front"));
            ticketList.add(new Ticket("Eva Green", "eva.green@example.com", "2025-08-17", 120.00, "VIP lounge access", "VIP", "Front"));
            ticketList.add(new Ticket("Frank Miller", "frank.miller@example.com", "2025-08-18", 55.00, "", "General", "Middle"));
            ticketList.add(new Ticket("Grace Kim", "grace.kim@example.com", "2025-08-18", 85.00, "Allergic to nuts", "Staff", "Back"));
            ticketList.add(new Ticket("Henry Clark", "henry.clark@example.com", "2025-08-19", 70.00, "Extra legroom seat", "VIP", "Front"));
            ticketList.add(new Ticket("Isabella Wright", "isabella.wright@example.com", "2025-08-19", 50.00, "", "General", "Middle"));
            ticketList.add(new Ticket("Jack Wilson", "jack.wilson@example.com", "2025-08-20", 95.00, "Needs interpreter", "Staff", "Back"));
    	}
        model.addAttribute("ArrayList", ticketList);
        System.out.println("Displaying ticket list of size: " + ticketList.size());
        return "Tickets";
    }

    @GetMapping("/about")
    public String about() {
        return "about";
    }

    @GetMapping("/schedule")
    public String schedule() {
        return "schedule";
    }

    @GetMapping("/faq")
    public String faq() {
        return "faq";
    }
}