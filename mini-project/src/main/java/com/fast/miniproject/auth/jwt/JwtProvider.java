package com.fast.miniproject.auth.jwt;

import com.fast.miniproject.auth.entity.User;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.stereotype.Component;

import java.util.Date;

@Component
public class JwtProvider {

    public String token(User user){
        Date date=new Date();

        return Jwts.builder()
                .setHeaderParam("typ","JWT")
                .setIssuer("Mini")
                .setIssuedAt(date)
                .setExpiration(new Date(date.getTime()+3600000))
                .claim("id",user.getMemberId())
                .claim("email",user.getEmail())
                .signWith(SignatureAlgorithm.HS256,"23029")
                .compact();
    }

    public Claims tokenToUser(String token){

        if(token!=null) {
            token = token.substring("Bearer".length());

            return Jwts.parser()
                    .setSigningKey("23029")
                    .parseClaimsJws(token)
                    .getBody();
        }else{
            return null;
        }

    }

}
