package ca.sheridancollege.sidhark.Beans;

import lombok.*;

@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class AnimeStore {
	
	private int id;
	private String name;
	private String manager;
	private String location;
	private String theme;
	
}
