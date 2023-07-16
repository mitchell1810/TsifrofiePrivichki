package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class DetectivesItem{

	@JsonProperty("firstName")
	private String firstName;

	@JsonProperty("lastName")
	private String lastName;

	@JsonProperty("violinPlayer")
	private boolean violinPlayer;

	@JsonProperty("MainId")
	private int mainId;

	@JsonProperty("categories")
	private List<CategoriesItem> categories;
}