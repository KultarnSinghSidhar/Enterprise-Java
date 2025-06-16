package ca.sheridancollege.sidhark.Beans;

import java.time.LocalDate;
import java.time.LocalTime;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class Employee {
	
	private int employeeId;
	private String storeName;
	private String name;
	private LocalDate shiftDate;
	private LocalTime shiftStartTime;
	private LocalTime shiftEndTime;
	private String role;
	private double hourlyRate;
	
}