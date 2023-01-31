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
public class CartItem {
    @JsonProperty("id")
    private Long cartItemId;
    private Long itemId;
    private String name;
    private String description;
    private Integer amount;

}
