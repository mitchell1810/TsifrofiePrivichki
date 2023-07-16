package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@NoArgsConstructor
@Setter
public class ExtraArrayItem{

	@JsonProperty("cap")
	private int cap;

	@JsonProperty("violin")
	private int violin;
}