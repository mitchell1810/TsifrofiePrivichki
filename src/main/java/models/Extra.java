package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class Extra{

	@JsonProperty("extraArray")
	private List<ExtraArrayItem> extraArray;
}