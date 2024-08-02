package com.clean.cleanroom.util;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.stereotype.Component;

public interface TokenService {

    String getEmailFromRequest(HttpServletRequest request);

}
