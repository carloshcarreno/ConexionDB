/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import model.Usuario;

/**
 *
 * @author CharliePC
 */
@Stateless
public class UsuarioDAO {

    private final static Logger LOGGER = Logger.getLogger("dao.UsuarioDAO");

    private EntityManagerFactory factory;
    private EntityManager em;

    public void crear(Usuario entity) {
        em.persist(entity);
    }

    public void editar(Usuario entity) {
        em.merge(entity);
    }

    public void eliminar(Usuario entity) {
        em.remove(em.merge(entity));
    }

    public Usuario encontrarUsuario(String correo) {
        return em.find(Usuario.class, correo);
    }

    public void crearConexion() {
        factory = Persistence.createEntityManagerFactory("Web_DBAccessPU");
        em = factory.createEntityManager();
    }

    public void cerrarConexion() {
        em.close();
    }

    public Usuario encontrarUsuarioPorLogin(String correo, String clave) {

        crearConexion();
        Query q = em.createQuery("SELECT u FROM Usuario u WHERE u.correo = :mail  AND u.clave = :pass ");
        q.setParameter("mail", correo);
        q.setParameter("pass", clave);

        try {
            return (Usuario) q.getSingleResult();
        } catch (NoResultException ex) {
            LOGGER.severe("ERROR AL CONSULTAR");
            return null;
        } catch (NonUniqueResultException ex2) {
            LOGGER.severe("ERROR AL CONSULTAR . DUPLICADO");
            return null;
        } finally {
            LOGGER.info("CONEXIÓN CERRADA");
            cerrarConexion();
        }

    }

    public List<Usuario> listar() {

        crearConexion();
        Query q = em.createQuery("SELECT u FROM Usuario u");

        try {
            return q.getResultList();
        } catch (Exception ex) {
            LOGGER.severe("ERROR AL CONSULTAR");
            return null;
        } finally {
            LOGGER.info("CONEXIÓN CERRADA");
            cerrarConexion();

        }

    }

}
