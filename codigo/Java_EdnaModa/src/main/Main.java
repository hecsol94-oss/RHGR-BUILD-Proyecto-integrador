package main;

// Importa la clase de la ventana de inicio de sesión
import vista.InicioSesion;

public class Main {

    // Método principal que se ejecuta al iniciar el programa
    public static void main(String[] arga) {

        // Crea una instancia de la ventana de inicio de sesión
        InicioSesion sesion = new InicioSesion();

        // Hace visible la ventana para que el usuario pueda interactuar con ella
        sesion.setVisible(true);
    }
}