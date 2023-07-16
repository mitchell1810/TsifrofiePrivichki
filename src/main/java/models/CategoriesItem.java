package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
public class CategoriesItem{

	@JsonProperty("CategoryID")
	private int categoryID;

	@JsonProperty("extra")
	private Extra extra;

	@JsonProperty("CategoryName")
	private String categoryName;
}