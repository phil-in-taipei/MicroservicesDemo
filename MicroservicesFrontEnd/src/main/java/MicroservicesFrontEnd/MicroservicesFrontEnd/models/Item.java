package MicroservicesFrontEnd.MicroservicesFrontEnd.models;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Item {
    @JsonProperty("itemId")
    private Long id;
    private Long cartItemId;
    private String name;
    private String description;
    private Integer amount;
}
