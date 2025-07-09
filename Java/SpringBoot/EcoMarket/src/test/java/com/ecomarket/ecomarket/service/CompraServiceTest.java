package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.exception.ResourceNotFoundException;
import com.ecomarket.ecomarket.model.Cliente;
import com.ecomarket.ecomarket.model.Compra;
import com.ecomarket.ecomarket.model.Sucursal;
import com.ecomarket.ecomarket.repository.CompraRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class CompraServiceTest {

    @Mock
    private CompraRepository compraRepository;

    @InjectMocks
    private CompraService compraService;

    private Compra compra1;
    private Compra compra2;
    private Cliente cliente;
    private Sucursal sucursal;
    private List<Compra> comprasList;

    @BeforeEach
    void setUp() {
        cliente = new Cliente("12345678", '9', "Juan", "Pérez");
        sucursal = new Sucursal();
        sucursal.setIdSucursal(1);

        compra1 = new Compra();
        compra1.setIdCompra(1);
        compra1.setFechaCompra(new Date());
        compra1.setNumeroFactura("F001");
        compra1.setCliente(cliente);
        compra1.setSucursal(sucursal);

        compra2 = new Compra();
        compra2.setIdCompra(2);
        compra2.setFechaCompra(new Date());
        compra2.setNumeroFactura("F002");
        compra2.setCliente(cliente);
        compra2.setSucursal(sucursal);

        comprasList = Arrays.asList(compra1, compra2);
    }

    @Test
    void testGetAllCompras() {
        // Given
        when(compraRepository.findAll()).thenReturn(comprasList);

        // When
        List<Compra> resultado = compraService.getAllCompras();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(0).getIdCompra());
        assertEquals(2, resultado.get(1).getIdCompra());
        assertEquals("F001", resultado.get(0).getNumeroFactura());
        assertEquals("F002", resultado.get(1).getNumeroFactura());
        verify(compraRepository).findAll();
    }

    @Test
    void testGetAllComprasListaVacia() {
        // Given
        when(compraRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Compra> resultado = compraService.getAllCompras();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(compraRepository).findAll();
    }

    @Test
    void testGetCompraByIdExistente() {
        // Given
        int idCompra = 1;
        when(compraRepository.findById(idCompra)).thenReturn(Optional.of(compra1));

        // When
        Compra resultado = compraService.getCompraById(idCompra);

        // Then
        assertNotNull(resultado);
        assertEquals(idCompra, resultado.getIdCompra());
        assertEquals("F001", resultado.getNumeroFactura());
        assertEquals(cliente, resultado.getCliente());
        assertEquals(sucursal, resultado.getSucursal());
        verify(compraRepository).findById(idCompra);
    }

    @Test
    void testGetCompraByIdNoExistente() {
        // Given
        int idCompra = 999;
        when(compraRepository.findById(idCompra)).thenReturn(Optional.empty());

        // When & Then
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            compraService.getCompraById(idCompra);
        });

        assertEquals("Compra no encontrado con ID: 999", exception.getMessage());
        verify(compraRepository).findById(idCompra);
    }

    @Test
    void testRepositoryInteractions() {
        // Given
        when(compraRepository.findAll()).thenReturn(comprasList);
        when(compraRepository.findById(1)).thenReturn(Optional.of(compra1));

        // When
        compraService.getAllCompras();
        compraService.getCompraById(1);

        // Then
        verify(compraRepository, times(1)).findAll();
        verify(compraRepository, times(1)).findById(1);
        verifyNoMoreInteractions(compraRepository);
    }

    @Test
    void testConstructorDependencyInjection() {
        // Verificar que el servicio se construye correctamente
        assertNotNull(compraService);
        assertNotNull(compraRepository);
    }

    @Test
    void testGetCompraByIdConDatosCompletos() {
        // Given
        Date fechaEspecifica = new Date();
        Compra compraCompleta = new Compra();
        compraCompleta.setIdCompra(100);
        compraCompleta.setFechaCompra(fechaEspecifica);
        compraCompleta.setNumeroFactura("FACT-2023-100");
        compraCompleta.setCliente(cliente);
        compraCompleta.setSucursal(sucursal);

        when(compraRepository.findById(100)).thenReturn(Optional.of(compraCompleta));

        // When
        Compra resultado = compraService.getCompraById(100);

        // Then
        assertNotNull(resultado);
        assertEquals(100, resultado.getIdCompra());
        assertEquals(fechaEspecifica, resultado.getFechaCompra());
        assertEquals("FACT-2023-100", resultado.getNumeroFactura());
        assertEquals("12345678", resultado.getCliente().getRun());
        assertEquals(1, resultado.getSucursal().getIdSucursal());
    }

    @Test
    void testGetCompraByIdConValoresLimite() {
        // Test con ID 0
        when(compraRepository.findById(0)).thenReturn(Optional.empty());
        ResourceNotFoundException exception = assertThrows(ResourceNotFoundException.class, () -> {
            compraService.getCompraById(0);
        });
        assertEquals("Compra no encontrado con ID: 0", exception.getMessage());

        // Test con ID negativo
        when(compraRepository.findById(-1)).thenReturn(Optional.empty());
        exception = assertThrows(ResourceNotFoundException.class, () -> {
            compraService.getCompraById(-1);
        });
        assertEquals("Compra no encontrado con ID: -1", exception.getMessage());

        verify(compraRepository).findById(0);
        verify(compraRepository).findById(-1);
    }
}
