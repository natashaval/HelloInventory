package hello.inven.helloinven.response;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class ResponseAjax {
    private String status;
    private Object data;

}
