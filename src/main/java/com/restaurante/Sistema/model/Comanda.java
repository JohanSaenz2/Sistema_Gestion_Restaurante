package com.restaurante.Sistema.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.restaurante.Sistema.Enum.EstadoComanda;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@Entity
@Table(name = "comandas", schema = "restaurante")
public class Comanda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "comanda_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "mesa_id")
    @JsonBackReference("mesa-comanda")    
    private Mesa mesa;

    @ManyToOne
    @JoinColumn(name = "empleado_id")
    @JsonBackReference("empleado-comanda") 
    private Empleado empleado;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private EstadoComanda estado;

    @Column(name = "total")
    private BigDecimal total;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @OneToMany(mappedBy = "comanda", cascade = CascadeType.ALL)
    @JsonManagedReference("comanda-detalle")  
    private List<DetalleComanda> detalles = new ArrayList<>();
}