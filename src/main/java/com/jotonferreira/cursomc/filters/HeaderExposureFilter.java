package com.jotonferreira.cursomc.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Component;

@Component
public class HeaderExposureFilter implements Filter{
	
	
	// filtro que vai interceptar todas as requisições e encaminha para seu ciclo normal
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {
		
		HttpServletResponse res = (HttpServletResponse) response;
		
		res.addHeader("access-control-expose-headers", "location"); // expoe explicitamente o location do novo endereço criado, podendo ser de uma nova categorias e outros, util para o Angular
		chain.doFilter(request, response);
		
	}

}
