package com.ecomarket.ecomarket.service;

import com.ecomarket.ecomarket.model.Cliente;
import com.ecomarket.ecomarket.repository.ClienteRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class ClienteServiceTest {

    @Mock
    private ClienteRepository clienteRepository;

    @InjectMocks
    private ClienteService clienteService;

    private Cliente cliente1;
    private Cliente cliente2;
    private List<Cliente> clientesList;

    @BeforeEach
    void setUp() {
        cliente1 = new Cliente("12345678", '9', "Juan", "Pérez");
        cliente2 = new Cliente("87654321", '0', "María", "González");
        clientesList = Arrays.asList(cliente1, cliente2);
    }

    @Test
    void testGetAllClientes() {
        // Given
        when(clienteRepository.findAll()).thenReturn(clientesList);

        // When
        List<Cliente> resultado = clienteService.getAllClientes();

        // Then
        assertNotNull(resultado);
        assertEquals(2, resultado.size());
        assertEquals("Juan", resultado.get(0).getNombres());
        assertEquals("María", resultado.get(1).getNombres());
        verify(clienteRepository).findAll();
    }

    @Test
    void testGetAllClientesListaVacia() {
        // Given
        when(clienteRepository.findAll()).thenReturn(Arrays.asList());

        // When
        List<Cliente> resultado = clienteService.getAllClientes();

        // Then
        assertNotNull(resultado);
        assertTrue(resultado.isEmpty());
        verify(clienteRepository).findAll();
    }

    @Test
    void testRepositoryInteraction() {
        // Given
        when(clienteRepository.findAll()).thenReturn(clientesList);

        // When
        clienteService.getAllClientes();

        // Then
        verify(clienteRepository, times(1)).findAll();
        verifyNoMoreInteractions(clienteRepository);
    }

    @Test
    void testConstructorDependencyInjection() {
        // Verificar que el servicio se construye correctamente con el repositorio
        // mockeado
        assertNotNull(clienteService);
        assertNotNull(clienteRepository);
    }

    @Test
    void testGetAllClientesConDatosCompletos() {
        // Given
        Cliente clienteCompleto = new Cliente("11111111", '1', "Carlos Alberto", "Rodríguez Martínez");
        when(clienteRepository.findAll()).thenReturn(Arrays.asList(clienteCompleto));

        // When
        List<Cliente> resultado = clienteService.getAllClientes();

        // Then
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        Cliente clienteResultado = resultado.get(0);
        assertEquals("11111111", clienteResultado.getRun());
        assertEquals('1', clienteResultado.getDv());
        assertEquals("Carlos Alberto", clienteResultado.getNombres());
        assertEquals("Rodríguez Martínez", clienteResultado.getApellidos());
    }
}
