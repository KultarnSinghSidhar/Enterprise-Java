package ca.sheridancollege.sidhark.Class;

import lombok.*;  	

@NoArgsConstructor
@AllArgsConstructor
@Data
public class Ticket {
    private String fullName;
    private String email;
    private String eventDate;
    private double price;
    private String specialRequests;

    private String ticketCategory;  
    private String seatArea;        
}