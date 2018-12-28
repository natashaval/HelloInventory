package hello.inven.helloinven.model;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAjax {
    private String status;
    private Object data;

}
