package com.restaurante.Sistema.config;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import com.restaurante.Sistema.Enum.RolEmpleados;
import com.restaurante.Sistema.model.Empleado;

@Component
public class SesionInterceptor implements HandlerInterceptor {

	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
			throws Exception {
Object empleadoObj = request.getSession().getAttribute("empleado");
		if (empleadoObj == null) {
			response.sendRedirect("/login");
			return false;
		}
		Empleado empleado =(Empleado) empleadoObj;
		String ruta = request.getRequestURI();
		if (ruta.startsWith("/admin")&& empleado.getRol() != RolEmpleados.administrador) {
			response.sendRedirect("/login");
			return false;
		}
		return true;
	}
}