package org.example;

import Entidades.*;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;


public class Main {
    public static void main(String[] args) {
        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("example-unit");

        EntityManager entityManager = entityManagerFactory.createEntityManager();
        System.out.println("En proceso");

        try {
            // Persistir una nueva entidad
            entityManager.getTransaction().begin();

            Factura factura1 = Factura.builder()
                    .numero(256)
                    .fecha("10/08/2024")
                    .build();

            Domicilio dom = Domicilio.builder()
                    .nombreCalle("Peltier")
                    .numero(165)
                    .build();

            Cliente cliente = Cliente.builder()
                    .nombre("José")
                    .apellido("Fuentes")
                    .dni(30565989)
                    .build();

            cliente.setDomicilio(dom);
            dom.setCliente(cliente);
            factura1.setCliente(cliente);

            Categoria perecederos = Categoria.builder()
                    .denominacion("perecederos")
                    .build();
            Categoria lacteos = Categoria.builder()
                    .denominacion("lacteos")
                    .build();
            Categoria limpieza = Categoria.builder()
                    .denominacion("limpieza")
                    .build();
            Articulo art1 = Articulo.builder()
                    .cantidad(200)
                    .denominacion("Yogurt, Ser Frut")
                    .precio(20)
                    .build();
            Articulo art2 = Articulo.builder()
                    .cantidad(300)
                    .denominacion("Detergente, Magistral")
                    .precio(80)
                    .build();
            //Categorias de art1

            art1.getCategorias().add(perecederos);
            art1.getCategorias().add(lacteos);
            lacteos.getArticulos().add(art1);
            perecederos.getArticulos().add(art1);
            //Categorias de art2
            art2.getCategorias().add(limpieza);
            limpieza.getArticulos().add(art2);

            DetalleFactura det1 = DetalleFactura.builder()
                    .build();
            det1.setArticulo(art1);
            det1.setCantidad(2);
            det1.setSubtotal(40);

            art1.getDetalles().add(det1);
            factura1.getDetalles().add(det1);
            det1.setFactura(factura1);

            DetalleFactura det2 = DetalleFactura.builder()
                    .build();

            det2.setArticulo(art2);
            det2.setCantidad(1);
            det2.setSubtotal(80);

            art2.getDetalles().add(det2);
            factura1.getDetalles().add(det2);
            det2.setFactura(factura1);

            factura1.setTotal(120);

            entityManager.persist(factura1);



            entityManager.flush();

            entityManager.getTransaction().commit();



        }catch (Exception e){

            entityManager.getTransaction().rollback();
            System.out.println(e.getMessage());
            System.out.println("No se pudo grabar la clase");}


        // Cerrar el EntityManager y el EntityManagerFactory
        entityManager.close();
        entityManagerFactory.close();
    }


}
