package jolyjdia.vk.sdk.objects.base;

import org.jetbrains.annotations.NonNls;

import java.util.List;

public class Error {
    private Integer error_code;
    private String error_msg;
    private List<RequestParam> request_params;

    public Integer getErrorCode() {
        return error_code;
    }

    public String getErrorMsg() {
        return error_msg;
    }

    public List<RequestParam> getRequestParams() {
        return request_params;
    }

    @Override
    public @NonNls String toString() {
        return "Error{" +
                "error_code=" + error_code +
                ", error_msg='" + error_msg + '\'' +
                ", request_params=" + request_params +
                '}';
    }
}
