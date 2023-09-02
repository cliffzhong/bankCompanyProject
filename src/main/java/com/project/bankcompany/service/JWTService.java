package com.project.bankcompany.service;


import com.project.bankcompany.dto.UserDto;
import io.jsonwebtoken.Claims;

public interface JWTService {

    String generateToken(UserDto userDto);

    Claims decryptJwtToken(String token);

    boolean hasTokenExpired(String token);

    boolean validateAccessToken(String token);
}
