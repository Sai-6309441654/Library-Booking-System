package com.Twg.SpringBoot.Library.JotModel;

import java.util.Date;
import java.util.HashMap;

import javax.crypto.SecretKey;

import org.springframework.stereotype.Service;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
@Service
public class JotService 
{

	public JotService() 
	{
		// TODO Auto-generated constructor stub
	}
	public static final String SECRET="SainathMyDairyRestApiSpringBootProjectSecurity";
	private SecretKey getSignedKey()
	{
		return Keys.hmacShaKeyFor(SECRET.getBytes());
	}
	public String generateJwt(String username,String role)
	{
		HashMap<String,Object> claims=new HashMap<>();
		claims.put("Role",role);
		return Jwts
				.builder()
				.subject(username)
		        .issuer(username)
		        .issuedAt(new Date(System.currentTimeMillis()))
		        .expiration(new Date(System.currentTimeMillis()+1800000))
		        .claims(claims)
		        .signWith(getSignedKey(),Jwts.SIG.HS256)
		        .compact();
	}
	public Claims verifySignatureAndExtractAllClaims(String token)
	{
		return Jwts
				.parser()
				.verifyWith(getSignedKey())
				.build()
				.parseSignedClaims(token)
				.getPayload();
	}
	public String extractUsername(String token)
	{
		return verifySignatureAndExtractAllClaims(token).getSubject();
	}
	public Date getExpirationDate(String token)
	{
		return verifySignatureAndExtractAllClaims(token).getExpiration();
	}
	public boolean isTokenExpired(String token)
	{
		return getExpirationDate(token).before(new Date());
	}	
    
}
