package com.Transaction.transaction.security;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.function.Function;

@Component
public class JwtService {
    private final String secret="\n" +
            "\n" +
            "655468576D5A7133743677397A24432646294A404E635266556A586E32723575";
    public String getUserNameFromToken(String token) {

        return getClaimsFromToken(token, Claims::getSubject);
    }

    private <T> T getClaimsFromToken(String token, Function<Claims,T> claimsResolver) {
        final Claims claims=getAllCalimsFromToken(token);
        return claimsResolver.apply(claims);
    }

    private Claims getAllCalimsFromToken(String token) {
        return Jwts.parserBuilder()
                .setSigningKey(getSingInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSingInKey() {
        byte[] keys= Decoders.BASE64.decode(secret);
        return Keys.hmacShaKeyFor(keys);
    }
    private boolean isTokenExpired(String token ){
    final Date date=getExpirationFromToken(token);
    return date.before(new Date());
    }

    private Date getExpirationFromToken(String token) {
        return getClaimsFromToken(token,Claims::getExpiration);
    }
    public String generateToken(UserDetails userDetails){

        Map<String,Object> claims=new HashMap<>();
        return doGenerateToken(claims,userDetails);

    }

    private String doGenerateToken(Map<String, Object> claims, UserDetails userDetails) {
        return Jwts.builder().setClaims(claims).setSubject(userDetails.getUsername()).
                setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis()+1000*24*60))
                .signWith(getSingInKey(), SignatureAlgorithm.HS256).compact();
    }

    public Boolean validationToken(String token, UserDetails userDetails) {
        final String username=getUserNameFromToken(token);
        return (username.equals(userDetails.getUsername()))&&!isTokenExpired(token);
    }
}
