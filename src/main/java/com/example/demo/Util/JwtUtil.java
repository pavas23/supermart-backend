package com.example.demo.Util;

import com.example.demo.Model.Customer;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtUtil {
    public static String secret = "bbb@supermart_secretKey#$%_";
    public String generateJwt(Customer customer){
        Date issuedAt = new Date();
        Claims claims = Jwts.claims().setIssuer(customer.getEmail().toString()).setIssuedAt(issuedAt);
        return Jwts.builder().setClaims(claims).compact();
    }
}
