package hello.inven.helloinven.response;

import lombok.*;

@Data

public class ResponseAjax {
    private String status;
    private Object data;

    public ResponseAjax() {
    }

    public ResponseAjax(String status, Object data) {
        this.status = status;
        this.data = data;
    }

    public ResponseAjax(String status) {
        this.status = status;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }
}
