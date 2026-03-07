
package com.wealthmanager.dto;


import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class VerifyOtpDTO {

    private String email;
    private String otp;

}