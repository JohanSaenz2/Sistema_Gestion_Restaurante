package com.restaurante.Sistema.patrones.factory;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.springframework.stereotype.Component;

import com.google.common.reflect.ClassPath;
import com.google.common.reflect.ClassPath.ClassInfo;
import com.restaurante.Sistema.anotacion.MiComponente;

@Component
public class FactoryGen {

	private final Map<String, Class<?>> componentes = new HashMap<>();

	public void init(String pkgName) {
		try {
			ClassPath classPath = ClassPath.from(getClass().getClassLoader());
			for (ClassInfo info : classPath.getTopLevelClassesRecursive(pkgName)) {
				Class<?> clase = info.load();
				MiComponente anotacion = clase.getAnnotation(MiComponente.class);
				if (anotacion != null) {
					componentes.put(anotacion.name(), clase);
				}
			}
		} catch (IOException e) {
			throw new RuntimeException("No se pudo escanear el paquete " + pkgName, e);
		}
	}

	@SuppressWarnings("unchecked")
	public <T> T crear(String name) {
		Class<?> clase = componentes.get(name);
		if (clase == null) {
			throw new RuntimeException("Componente " + name + " no está registrado");
		}
		try {
			return (T) clase.getDeclaredConstructor().newInstance();
		} catch (ReflectiveOperationException e) {
			throw new RuntimeException("No se pudo instanciar el componente " + name, e);
		}
	}
}
