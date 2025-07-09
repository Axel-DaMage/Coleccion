package com.ecomarket.ecomarket.controller;

import com.ecomarket.ecomarket.model.Cliente;
import com.ecomarket.ecomarket.service.ClienteService;
import com.ecomarket.ecomarket.util.utils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(MockitoExtension.class)
@WebMvcTest(ClienteController.class)
class ClienteControllerTest {

        @Autowired
        private MockMvc mockMvc;

        @MockBean
        private ClienteService clienteService;

        @Autowired
        private ObjectMapper objectMapper;

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
        void testGetAllClientes() throws Exception {
                when(clienteService.getAllClientes()).thenReturn(clientesList);

                mockMvc.perform(get("/api/clientes"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/hal+json"))
                                .andExpect(jsonPath("$._embedded.clienteList", hasSize(2)))
                                .andExpect(jsonPath("$._embedded.clienteList[0].run", is("12345678")))
                                .andExpect(jsonPath("$._embedded.clienteList[0].dv", is("9")))
                                .andExpect(jsonPath("$._embedded.clienteList[0].nombres", is("Juan")))
                                .andExpect(jsonPath("$._embedded.clienteList[0].apellidos", is("Pérez")))
                                .andExpect(jsonPath("$._embedded.clienteList[1].run", is("87654321")))
                                .andExpect(jsonPath("$._embedded.clienteList[1].dv", is("0")))
                                .andExpect(jsonPath("$._embedded.clienteList[1].nombres", is("María")))
                                .andExpect(jsonPath("$._embedded.clienteList[1].apellidos", is("González")));

                verify(clienteService).getAllClientes();
        }

        @Test
        void testGetAllClientesListaVacia() throws Exception {

                when(clienteService.getAllClientes()).thenReturn(Arrays.asList());

                mockMvc.perform(get("/api/clientes"))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/hal+json"));

                verify(clienteService).getAllClientes();
        }

        @Test
        void testGetClienteByRunExistente() throws Exception {
                String run = "12345678";
                when(clienteService.getClienteByRun(run)).thenReturn(cliente1);

                mockMvc.perform(get("/api/clientes/{run}", run))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/hal+json"))
                                .andExpect(jsonPath("$.run", is("12345678")))
                                .andExpect(jsonPath("$.dv", is("9")))
                                .andExpect(jsonPath("$.nombres", is("Juan")))
                                .andExpect(jsonPath("$.apellidos", is("Pérez")));

                verify(clienteService).getClienteByRun(run);
        }

        @Test
        void testGetClienteByRunNoExistente() throws Exception {
                String run = "99999999";
                when(clienteService.getClienteByRun(run))
                                .thenThrow(new com.ecomarket.ecomarket.exception.ResourceNotFoundException(
                                                "Cliente no encontrado con RUN: " + run));

                mockMvc.perform(get("/api/clientes/{run}", run))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.error").value("Not Found"))
                                .andExpect(jsonPath("$.message").value("Cliente no encontrado con RUN: " + run));

                verify(clienteService).getClienteByRun(run);
        }

        @Test
        void testCreateCliente() throws Exception {

                Cliente nuevoCliente = new Cliente("21545655", '2', "Carlos", "Rodríguez");
                assertTrue(utils.esRutValido(nuevoCliente.getRun(), nuevoCliente.getDv()), "El RUT no es válido");
                when(clienteService.createCliente(any(Cliente.class))).thenReturn(nuevoCliente);

                mockMvc.perform(post("/api/clientes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(nuevoCliente)))
                                .andExpect(status().isCreated())
                                .andExpect(content().contentType("application/hal+json"))
                                .andExpect(jsonPath("$.run", is("21545655")))
                                .andExpect(jsonPath("$.dv", is("2")))
                                .andExpect(jsonPath("$.nombres", is("Carlos")))
                                .andExpect(jsonPath("$.apellidos", is("Rodríguez")));

                verify(clienteService).createCliente(any(Cliente.class));
        }

        @Test
        void testUpdateClienteExistente() throws Exception {

                String run = "12345678";
                Cliente clienteActualizado = new Cliente(run, '9', "Juan Carlos", "Pérez López");
                when(clienteService.updateCliente(eq(run), any(Cliente.class))).thenReturn(clienteActualizado);

                mockMvc.perform(put("/api/clientes/{run}", run)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(clienteActualizado)))
                                .andExpect(status().isOk())
                                .andExpect(content().contentType("application/hal+json"))
                                .andExpect(jsonPath("$.run", is("12345678")))
                                .andExpect(jsonPath("$.nombres", is("Juan Carlos")))
                                .andExpect(jsonPath("$.apellidos", is("Pérez López")));

                verify(clienteService).updateCliente(eq(run), any(Cliente.class));
        }

        @Test
        void testUpdateClienteNoExistente() throws Exception {
                String run = "99999999";
                Cliente cliente = new Cliente(run, '1', "No", "Existe");
                when(clienteService.updateCliente(eq(run), any(Cliente.class)))
                                .thenThrow(new com.ecomarket.ecomarket.exception.ResourceNotFoundException(
                                                "Cliente no encontrado con RUN: " + run));

                mockMvc.perform(put("/api/clientes/{run}", run)
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(cliente)))
                                .andExpect(status().isNotFound())
                                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                                .andExpect(jsonPath("$.status").value(404))
                                .andExpect(jsonPath("$.error").value("Not Found"))
                                .andExpect(jsonPath("$.message").value("Cliente no encontrado con RUN: " + run));

                verify(clienteService).updateCliente(eq(run), any(Cliente.class));
        }

        @Test
        void testDeleteCliente() throws Exception {
                String run = "12345678";
                doNothing().when(clienteService).deleteCliente(run);

                mockMvc.perform(delete("/api/clientes/{run}", run))
                                .andExpect(status().isNoContent());

                verify(clienteService).deleteCliente(run);
        }

        @Test
        void testCreateClienteConDatosIncompletos() throws Exception {
                Cliente clienteIncompleto = new Cliente();
                clienteIncompleto.setRun("11111111");

                when(clienteService.createCliente(any(Cliente.class))).thenReturn(clienteIncompleto);

                mockMvc.perform(post("/api/clientes")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content(objectMapper.writeValueAsString(clienteIncompleto)))
                                .andExpect(status().isCreated())
                                .andExpect(jsonPath("$.run", is("11111111")));

                verify(clienteService).createCliente(any(Cliente.class));
        }

        @Test
        void testGetClienteByRunConCaracteresEspeciales() throws Exception {

                String runEspecial = "12345678-9";
                when(clienteService.getClienteByRun(runEspecial))
                                .thenThrow(new com.ecomarket.ecomarket.exception.ResourceNotFoundException(
                                                "Cliente no encontrado con RUN: " + runEspecial));

                mockMvc.perform(get("/api/clientes/{run}", runEspecial))
                                .andExpect(status().isNotFound());

                verify(clienteService).getClienteByRun(runEspecial);
        }
}
