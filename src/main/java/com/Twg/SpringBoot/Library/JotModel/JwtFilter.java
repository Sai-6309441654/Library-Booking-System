package com.Twg.SpringBoot.Library.JotModel;

import java.io.IOException;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
@Component
public class JwtFilter extends OncePerRequestFilter
{
	@Autowired
    private JotService jotService;
	public JwtFilter() {
		// TODO Auto-generated constructor stub
	}

	@Override
	protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
			throws ServletException, IOException 
	{
		String authheader=request.getHeader("Authorization");
		String token=null;
		if(authheader!=null&&authheader.startsWith("Bearer "))
		{
			token=authheader.substring(7);
		}
		
		if(token!=null && SecurityContextHolder.getContext().getAuthentication()==null)
		{
			try
			{
			Claims claims=jotService.verifySignatureAndExtractAllClaims(token);
			String role=claims.get("Role",String.class);
			System.out.println(role+"in Filter");
			List<SimpleGrantedAuthority> simpleGrantedAuthorities=List.of(new SimpleGrantedAuthority("ROLE_"+role));
			if(!jotService.isTokenExpired(token))
			{
				UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken=new UsernamePasswordAuthenticationToken(claims.getSubject(),null,simpleGrantedAuthorities);
				usernamePasswordAuthenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
				SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
			}
			}
			catch(Exception e)
			{
				SecurityContextHolder.clearContext();
				response.sendError(HttpServletResponse.SC_UNAUTHORIZED, "Invalid or expired token");
			}
		}
		filterChain.doFilter(request, response);
	}

}
