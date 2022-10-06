/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package Controlador;

import Modelo.Articulo;
import Modelo.ClsConexion;
import Modelo.Usuario;
import Modelo.Venta;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cardo
 */
public class Controlador {

    ClsConexion conexion = new ClsConexion();

    public Controlador() {

    }

    public boolean guardarUsuario(String nombre, String apellido, String correo, String contraseña, long cedula) {

        Usuario usuario = new Usuario(nombre, apellido, correo, contraseña, cedula);

        conexion.conectar();

        try {

            conexion.getSentenciaSQL().execute("insert into usuario(nombre,apellido,correo,contraseña, cedula) "
                    + "values('" + usuario.getNombre() + "','"
                    + usuario.getApellido() + "','"
                    + usuario.getCorreo() + "','"
                    + usuario.getContraseña() + "',"
                    + usuario.getCedula() + ")");
            conexion.desconectar();
            return true;

        } catch (SQLException ex) {
            conexion.desconectar();
            return false;
        }
    }

    public boolean guardarArticulo(String nombre, String descripcion, String categoria, Double precio, int cantidad) {

        Articulo articulo = new Articulo(nombre, descripcion, categoria, precio, cantidad);

        conexion.conectar();

        try {

            conexion.getSentenciaSQL().execute("insert into articulo(nombre,descripcion,categoria,precio, cantidad) "
                    + "values('" + articulo.getNombre() + "','"
                    + articulo.getDescripcion() + "','"
                    + articulo.getCategoria() + "',"
                    + articulo.getPrecio() + ","
                    + articulo.getCantidad() + ")");
            conexion.desconectar();
            return true;

        } catch (SQLException ex) {
            conexion.desconectar();
            return false;
        }
    }

    public List<String> buscarUsuarioCorreo(String correo) {
        List<String> temp = new ArrayList<String>();

        conexion.conectar();

        try {
            conexion.setResultadoDB(conexion.getSentenciaSQL().
                    executeQuery("select nombre,apellido,correo,"
                            + "contraseña, cedula from usuario where "
                            + "correo='" + correo + "'"));

            if (conexion.getResultadoDB().next()) {
                temp.add(conexion.getResultadoDB().getString("nombre"));
                temp.add(conexion.getResultadoDB().getString("apellido"));
                temp.add(conexion.getResultadoDB().getString("correo"));
                temp.add(conexion.getResultadoDB().getString("contraseña"));
                temp.add(conexion.getResultadoDB().getLong("cedula") + "");
            }
            conexion.desconectar();
        } catch (SQLException ex) {
            Logger.getLogger(Controlador.class.getName())
                    .log(Level.SEVERE, null, ex);
            conexion.desconectar();
        }
        return temp;
    }

    public boolean loguearUsuario(String correo, String contraseña) {

        List<String> temp = new ArrayList<String>();
        temp = buscarUsuarioCorreo(correo);

        if (temp.get(2).equalsIgnoreCase(correo) && temp.get(3).equals(contraseña)) {
            return true;
        } else {
            return false;
        }
    }

    public boolean guadarVenta(String fechaVenta, String nombreArticulo, int unidadesVendidas, Double valorTotal) {

        Venta venta = new Venta(fechaVenta, nombreArticulo, unidadesVendidas, valorTotal);

        conexion.conectar();

        try {

            conexion.getSentenciaSQL().execute("insert into venta(fechaVenta,nombreArticulo,unidadesVendidas,valorTotal) "
                    + "values('" + venta.getFechaVenta() + "','"
                    + venta.getNombreArticulo() + "',"
                    + venta.getUnidadesVendidas() + ","
                    + venta.getValorTotal() + ")");
            conexion.desconectar();
            return true;

        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conexion.desconectar();
            return false;
        }
    }

    public boolean modificarUsuario(String nombre, String apellido, String correo, String contraseña, long cedula) {
        Usuario usuario = new Usuario(nombre, apellido, correo, contraseña, cedula);
        conexion.conectar();
        try {
            conexion.getSentenciaSQL().execute("update usuario set nombre='" + usuario.getNombre()
                    + "',apellido='" + usuario.getApellido() + "',"
                    + "contraseña='" + usuario.getContraseña() + "'"
                    + " where correo='" + usuario.getCorreo() + "'");
            conexion.desconectar();
            return true;
        } catch (SQLException ex) {
            System.out.println(ex.getMessage());
            conexion.desconectar();
            return false;
        }
    }
    
      public boolean eliminarUsuario(String correo) {

        conexion.conectar();

        try {
            conexion.getSentenciaSQL().execute("delete from usuario where correo='" + correo + "'");
            conexion.desconectar();
            return true;
        } catch (SQLException ex) {
            conexion.desconectar();
            return false;
        }
    }

}
