package com.github.erickluz.resource;

import static io.restassured.RestAssured.given;

import org.junit.jupiter.api.MethodOrderer.OrderAnnotation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.github.erickluz.domain.Usuario;

import io.quarkus.test.common.QuarkusTestResource;
import io.quarkus.test.h2.H2DatabaseTestResource;
import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;

@QuarkusTest
@QuarkusTestResource(H2DatabaseTestResource.class)
@TestMethodOrder(OrderAnnotation.class)
public class UsuarioResourceTest {

    @Test
    public void testAdd() {
    	Usuario usuario = new Usuario();
    	usuario.setNome("Erick");
    	usuario.setEmail("erick@luz.com");
    	
    	given().contentType(ContentType.JSON).body(usuario)
    	.when().post("/usuarios").then().statusCode(201);
    }
}
