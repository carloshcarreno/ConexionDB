/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controllers;

import dao.UsuarioDAO;
import java.io.IOException;
import javax.inject.Named;
import javax.enterprise.context.SessionScoped;
import java.io.Serializable;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.EJB;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import model.Usuario;

/**
 *
 * @author CharliePC
 */
@Named(value = "loginController")
@SessionScoped
public class LoginController implements Serializable {

    private Usuario usuario;
    private Usuario usuarioAutenticado = null;
    List<Usuario> listado;

    private final static Logger LOGGER = Logger.getLogger("controller.LoginController");

    @EJB
    private UsuarioDAO ejbDao;

    /**
     * Creates a new instance of LoginController
     */
    public LoginController() {
        usuario = new Usuario();
    }

    public void login() throws IOException {

        FacesContext ctx = FacesContext.getCurrentInstance();

        usuarioAutenticado = ejbDao.encontrarUsuarioPorLogin(usuario.getCorreo(), usuario.getClave());

        if (usuarioAutenticado != null) {
            LOGGER.log(Level.INFO, "BIENVENIDO");
            ctx.getExternalContext().redirect("home.xhtml");
        } else {
            LOGGER.log(Level.SEVERE, "NO ENCONTRADO");
            ctx.getExternalContext().redirect("index.xhtml");
        }

        usuario = new Usuario();
    }

    public List<Usuario> getListado() {
        listado = ejbDao.listar();
        return listado;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

}
