package com.yavirac.homies.Seguridad.Auth;
import java.util.List;

import lombok.Data;

@Data
public class RecaptchaResponse {
    private boolean success;
    private List<String> errorCodes;
}