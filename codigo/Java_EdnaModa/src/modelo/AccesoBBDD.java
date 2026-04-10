package modelo;

import java.sql.*;
import java.util.ArrayList;

public class AccesoBBDD {
	
	// Configuración de los parámetros de conexión a MySQL
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/TallerEdnaModa";
	private String usuario = "root";
	private String pword = "12345678";
	
    public Connection abrirConexion() {
		
		Connection conexion = null;
		
		try {
			// Carga del driver y establecimiento de la conexión
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, pword);
			
		} catch(Exception e) {
			System.out.println("Error al conectar con la BD:");
			e.printStackTrace();
		}
		
		return conexion;
	}
	
	public void cerrarConexion(Connection c) {
		try {
		    c.close();
		} catch(SQLException e) {
			e.printStackTrace();
		}
	}
	
	public void limpiarTablas(Connection c) throws SQLException {
	    Statement st = c.createStatement();
	    
	    // Desactivación de restricciones para limpiar tablas con claves ajenas
	    st.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");
	    
	    // Vaciado de tablas y reinicio de contadores AUTO_INCREMENT
	    st.executeUpdate("TRUNCATE TABLE Citas");
	    st.executeUpdate("TRUNCATE TABLE Traje");
	    st.executeUpdate("TRUNCATE TABLE Cliente");
	    st.executeUpdate("TRUNCATE TABLE Empleados");
	    st.executeUpdate("TRUNCATE TABLE Taller");
	    
	    st.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");
	    
	    st.close();
	}
	
	public void insertarClientes(Connection c) throws SQLException {
		Statement st = c.createStatement();
	    ArrayList<String> queryC = new ArrayList<>();

	    // Lista de sentencias SQL para dar de alta a los personajes
	    queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Mr. Increíble', 'superhéroe', 'superfuerza', 'rojo y azul')");
	    // ... resto de clientes ...
        queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Silbido', 'superhéroe', 'chillido agudo', 'negro')");

	    // Ejecución masiva de los inserts
	    for (String query : queryC) {
	        st.executeUpdate(query);
	    }
	    st.close();
	}
	
	public ArrayList<Cliente> recogeClientes(Connection c) throws SQLException {
	    ArrayList<Cliente> clientes = new ArrayList<>();
	    Statement st = c.createStatement();
	    ResultSet resultados = st.executeQuery("SELECT * FROM Cliente");

	    // Bucle para convertir cada fila de la BBDD en un objeto de la clase Cliente
	    while (resultados.next()) {
	    	int idClientes = resultados.getInt("id_cliente");
	        String nombre = resultados.getString("nombre_personaje");
	        String tipo = resultados.getString("tipo_heroe");
	        String poder = resultados.getString("superpoder");
	        String colores = resultados.getString("colores");

	        Cliente cliente = new Cliente(idClientes, nombre, tipo, poder, colores);
	        clientes.add(cliente);
	    }
	    resultados.close();
	    st.close();
	    return clientes;
	}

    // (El resto de métodos insertar y recoge siguen la misma lógica comentada arriba)

	
	public void insertarEmpleados(Connection c) throws SQLException {
		
		Statement st = c.createStatement();
	    
	    ArrayList<String> queryE = new ArrayList<>();
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('aprendiz', 'Lucía', 'Martínez', 'Aguja', 'lucia', 'Lucia2026')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('aprendiz', 'Carlos', 'Ruiz', 'Tijeras', 'carlos', 'Tijeras123')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('oficial', 'Ana', 'Torres', 'SastreX', 'ana', 'SastreX2026')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('maestro', 'Javier', 'Gómez', 'MaestroModa', 'javier', 'MaestroModa!')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('aprendiz', 'María', 'Delgado', 'Costurilla', 'maria', 'Costurilla22')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('aprendiz', 'Pablo', 'Herrera', 'HiloFino', 'pablo', 'HiloFino33')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('oficial', 'Sofía', 'Navarro', 'PatrónX', 'sofia', 'PatronX44')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('oficial', 'Diego', 'Fernández', 'CorteMaestro', 'diego', 'CorteMaestro55')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('maestro', 'Elena', 'Rivas', 'DamaAguja', 'elena', 'DamaAguja66')");
	    queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contrasena) VALUES ('maestro', 'Tomás', 'Villalba', 'GranSastre', 'tomas', 'GranSastre77')");

	    for (String q : queryE) { st.executeUpdate(q); }
	    st.close();
	}

	public ArrayList<Empleado> recogeEmpleados(Connection c) throws SQLException {
	    ArrayList<Empleado> empleados = new ArrayList<>();
	    Statement st = c.createStatement();
	    ResultSet resultados = st.executeQuery("SELECT * FROM Empleados");

	    while (resultados.next()) {
	        // Declaración de variables según las columnas de la tabla
	    	int idEmpleado = resultados.getInt("id_empleado");
	        String categoria = resultados.getString("categoria");
	        String nombre = resultados.getString("nombre");
	        String apellido = resultados.getString("apellido");
	        String apodo = resultados.getString("apodo");
	        String usuario = resultados.getString("usuario");
	        String contrasena = resultados.getString("contrasena");

	        // Creación del objeto e inserción en la lista
	        Empleado empleado = new Empleado(idEmpleado, categoria, nombre, apellido, apodo, usuario, contrasena);
	        empleados.add(empleado);
	    }

	    resultados.close();
	    st.close();
	    return empleados;
	}
	
	public void insertarTalleres(Connection c) throws SQLException {
		
		Statement st = c.createStatement();
	    
	    ArrayList<String> queryT = new ArrayList<>();
	    queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Milán', 'diseño');");
	    queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('París', 'diseño');");
	    queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Madrid', 'costura');");
	    queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Nueva York', 'costura');");
	    queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Berlín', 'pruebas');");

	    for (String q : queryT) { st.executeUpdate(q); }
	    st.close();
	}
	
	public ArrayList<Taller> recogeTalleres(Connection c) throws SQLException {
	    ArrayList<Taller> talleres = new ArrayList<>();
	    Statement st = c.createStatement();
	    ResultSet resultados = st.executeQuery("SELECT * FROM Taller");

	    while (resultados.next()) {
	    	int id_sala = resultados.getInt("id_sala");
	        String nombreSala = resultados.getString("nombre_sala");
	        String tipoSala = resultados.getString("tipo_sala");

	        Taller taller = new Taller(id_sala, nombreSala, tipoSala);
	        talleres.add(taller);
	    }

	    resultados.close();
	    st.close();
	    return talleres;
	}
	
	public void insertarTrajes(Connection c) throws SQLException {
		
		Statement st = c.createStatement();
	    
	    ArrayList<String> queryT = new ArrayList<>();

	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje principal de Mr. Increíble', 'pruebas', 1)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje reforzado para misiones pesadas', 'costura', 1)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje elástico de alta resistencia', 'pruebas', 2)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de infiltración flexible', 'diseño', 2)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje aerodinámico de velocidad', 'costura', 3)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje antifricción para carreras', 'diseño', 3)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de invisibilidad optimizado', 'pruebas', 4)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de campos de fuerza', 'costura', 4)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Armadura tecnológica de Síndrome', 'diseño', 5)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de vuelo mejorado', 'costura', 5)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje ignífugo para Jack-Jack', 'pruebas', 6)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje multirresistente para poderes variables', 'diseño', 6)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje térmico de hielo', 'costura', 7)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje anti-condensación', 'diseño', 7)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de excavación subterránea', 'diseño', 8)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje blindado anti-derrumbes', 'costura', 8)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje explosivo reforzado', 'pruebas', 9)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje ignífugo anti-detonación', 'diseño', 9)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de hipnosis visual', 'costura', 10)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de camuflaje digital', 'diseño', 10)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje estabilizador de portales', 'pruebas', 11)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje anti-distorsión espacial', 'costura', 11)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje conductor de electricidad', 'costura', 12)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje aislante de alto voltaje', 'diseño', 12)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de concentración telequinética', 'diseño', 13)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje reforzado para control mental', 'costura', 13)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje resistente a lava', 'pruebas', 14)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje térmico de contención', 'diseño', 14)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de superfuerza reforzado', 'pruebas', 15)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de impacto pesado', 'costura', 15)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje de protección auditiva', 'costura', 16)");
	    queryT.add("INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('Traje amplificador de ondas sonoras', 'diseño', 16)");

	    for (String q : queryT) {
	        st.executeUpdate(q);
	    }
	    st.close();
	}
	
	public ArrayList<Traje> recogeTrajes(Connection c) throws SQLException {
	    ArrayList<Traje> trajes = new ArrayList<>();
	    Statement st = c.createStatement();
	    ResultSet resultados = st.executeQuery("SELECT * FROM Traje");

	    while (resultados.next()) {
	    	int idTraje = resultados.getInt("id_traje");
	        String nombreTraje = resultados.getString("nombre_traje");
	        String estado = resultados.getString("estado");
	        int idCliente = resultados.getInt("id_cliente");

	        Traje traje = new Traje(idTraje, nombreTraje, estado, idCliente);
	        trajes.add(traje);
	    }

	    resultados.close();
	    st.close();
	    return trajes;
	}
	
	public void insertarCitas(Connection c) throws SQLException {
		
		Statement st = c.createStatement();
	    
	    ArrayList<String> queryCi = new ArrayList<>();

	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-10', '09:00', 1, 1, 3)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-10', '10:00', 1, 2, 1)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-10', '11:00', 1, 3, 3)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-10', '12:00', 1, 4, 5)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-10', '15:00', 1, 5, 1)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-10', '16:00', 1, 8, 4)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-10', '17:00', 1, 9, 4)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-11', '09:00', 1, 6, 5)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-11', '10:00', 1, 7, 3)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-11', '11:00', 1, 11, 5)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-11', '12:00', 1, 12, 3)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-11', '15:00', 1, 10, 1)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-11', '16:00', 1, 16, 4)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-12', '09:00', 1, 13, 3)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-12', '10:00', 1, 14, 5)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-12', '11:00', 1, 15, 4)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-12', '15:00', 1, 5, 1)");
	    queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala) VALUES ('2026-04-12', '16:00', 1, 8, 4)");

	    for (String q : queryCi) {
	        st.executeUpdate(q);
	    }
	    st.close();
	}
	
	public ArrayList<Cita> recogeCitas(Connection c) throws SQLException {
	    ArrayList<Cita> citas = new ArrayList<>();
	    Statement st = c.createStatement();
	    ResultSet resultados = st.executeQuery("SELECT * FROM Citas");

	    while (resultados.next()) {
	        // Extraemos el ID que MySQL creó automáticamente
	        int id = resultados.getInt("id_cita"); 
	        Date fecha = resultados.getDate("fecha");
	        Time hora = resultados.getTime("hora_inicio");
	        int duracion = resultados.getInt("duracion");
	        int idCliente = resultados.getInt("id_cliente");
	        int idSala = resultados.getInt("id_sala");

	        // Se lo pasamos al constructor de tu clase Cita
	        Cita cita = new Cita(id, fecha, hora, duracion, idCliente, idSala);
	        citas.add(cita);
	    }

	    resultados.close();
	    st.close();
	    return citas;
	}

}
