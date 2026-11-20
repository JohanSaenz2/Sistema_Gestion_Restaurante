package com.restaurante.Sistema;


import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import javax.sql.DataSource;
import java.sql.Connection;

@Component
public class TestConexion implements CommandLineRunner {

 private final DataSource dataSource;

 public TestConexion(DataSource dataSource) {
     this.dataSource = dataSource;
 }

 @Override
 public void run(String... args) throws Exception {
     try (Connection conn = dataSource.getConnection()) {
         System.out.println("=================================");
         System.out.println("✅ CONEXIÓN EXITOSA");
         System.out.println("Base de datos: " + conn.getCatalog());
         System.out.println("URL: " + conn.getMetaData().getURL());
         System.out.println("=================================");
     } catch (Exception e) {
         System.out.println("=================================");
         System.out.println("❌ ERROR DE CONEXIÓN: " + e.getMessage());
         System.out.println("=================================");
     }
 }
}