package models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import java.util.List;

@Getter
@NoArgsConstructor
@Setter
public class TestObject {

	@JsonProperty("success")
	private boolean success;

	@JsonProperty("detectives")
	private List<DetectivesItem> detectives;
}