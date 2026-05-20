package modelo;

import java.util.ArrayList;
import java.sql.*;

public class AccesoBBDD {

	// Configuración de los parámetros de conexión a MySQL
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://localhost/tallerednamoda";
	private String usuario = "root";
	private String pword = "1234";

	/**
	 * 
	 * @return
	 */
	public Connection abrirConexion() {

		Connection conexion = null;

		try {
			// Carga del driver y establecimiento de la conexión
			Class.forName(driver);
			conexion = DriverManager.getConnection(url, usuario, pword);

		} catch (Exception e) {
			System.out.println("Error al conectar con la BD:");
			e.printStackTrace();
		}

		return conexion;
	}

	/**
	 * 
	 * @param c
	 */
	public void cerrarConexion(Connection c) {
		try {
			c.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void limpiarTablas(Connection c) throws SQLException {
		Statement st = c.createStatement();

		// Desactivación de restricciones para limpiar tablas con claves ajenas
		st.executeUpdate("SET FOREIGN_KEY_CHECKS = 0");

		// Vaciado de tablas y reinicio de contadores AUTO_INCREMENT
		st.executeUpdate("TRUNCATE TABLE Cita_Aprendiz");
		st.executeUpdate("TRUNCATE TABLE Citas");
		st.executeUpdate("TRUNCATE TABLE Traje");
		st.executeUpdate("TRUNCATE TABLE Cliente");
		st.executeUpdate("TRUNCATE TABLE Empleados");
		st.executeUpdate("TRUNCATE TABLE Taller");

		st.executeUpdate("SET FOREIGN_KEY_CHECKS = 1");

		st.close();
	}

	/**
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void insertarClientes(Connection c) throws SQLException {
		Statement st = c.createStatement();
		ArrayList<String> queryC = new ArrayList<>();

		// Lista de sentencias SQL para dar de alta a los personajes
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Mr. Increíble', 'superhéroe', 'superfuerza', 'rojo y azúl');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Elastigirl', 'superheroína', 'elasticidad', 'rojo y blanco');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Dash', 'superhéroe', 'supervelocidad', 'rojo y negro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Violeta', 'superheroína', 'invisible y campos fuerza', 'rojo y negro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Síndrome', 'supervillano', 'inventos', 'blanco y negro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Jack-Jack', 'superhéroe', 'multipoderes', 'rojo y negro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Frozono', 'superhéroe', 'hielo', 'blanco y azúl claro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('The Underminer', 'supervillano', 'excavar', 'marrón y amarillo');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Bomb Voyage', 'supervillano', 'bombas atómicas', 'blanco y negro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Rapta-Pantallas', 'supervillana', 'hipnosis', 'negro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Vacío', 'superheroína', 'portales', 'verde y azúl');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Helectrix', 'superhéroe', 'electricidad', 'azúl oscuro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Krujido', 'superhéroe', 'telequinesia', 'azúl oscuro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Reflujo', 'superhéroe', 'lava', 'naranja');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Bloque', 'superheroína', 'superfuerza', 'marrón y negro');");
		queryC.add("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('Silbido', 'superhéroe', 'chillido agudo', 'negro');");

		// Ejecución masiva de los inserts
		for (String query : queryC) {
			st.executeUpdate(query);
		}
		st.close();
	}

	/**
	 * 
	 * @param c
	 * @return
	 * @throws SQLException
	 */
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
	
	

	// (El resto de métodos insertar y recoge siguen la misma lógica comentada
	// arriba)

	/**
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void insertarEmpleados(Connection c) throws SQLException {

		Statement st = c.createStatement();

		ArrayList<String> queryE = new ArrayList<>();
		// Lista de sentencias SQL para dar de alta a los empleados
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Lucía', 'Martínez', 'Aguja', 'lucia', 'Lucia2026')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Carlos', 'Ruiz', 'Tijeras', 'carlos', 'Tijeras123')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Ana', 'Torres', 'SastreX', 'ana', 'SastreX2026')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Javier', 'Gómez', 'MaestroModa', 'javier', 'MaestroModa!')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'María', 'Delgado', 'Costurilla', 'maria', 'Costurilla22')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Pablo', 'Herrera', 'HiloFino', 'pablo', 'HiloFino33')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Sofía', 'Navarro', 'PatrónX', 'sofia', 'PatronX44')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Diego', 'Fernández', 'CorteMaestro', 'diego', 'CorteMaestro55')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Elena', 'Rivas', 'DamaAguja', 'elena', 'DamaAguja66')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Tomás', 'Villalba', 'GranSastre', 'tomas', 'GranSastre77')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Laura', 'Sánchez', 'Dedal', 'laura', 'Dedal2026')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Miguel', 'Ortega', 'Puntada', 'miguel', 'Puntada123')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Carmen', 'Vega', 'HiloRojo', 'carmen', 'HiloRojo456')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('aprendiz', 'Álvaro', 'Castro', 'Costurero', 'alvaro', 'Costurero789')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('oficial', 'Raquel', 'Molina', 'PatrónPro', 'raquel', 'PatronPro321')");
		queryE.add("INSERT INTO Empleados (categoria, nombre, apellido, apodo, usuario, contraseña) VALUES ('maestro', 'Fernando', 'Ibáñez', 'AltaCostura', 'fernando', 'AltaCostura999')");

		for (String q : queryE) {
			st.executeUpdate(q);
		}
		st.close();
	}

	/**
	 * 
	 * @param c
	 * @return
	 * @throws SQLException
	 */
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
			String contrasena = resultados.getString("contraseña");

			// Creación del objeto e inserción en la lista
			Empleado empleado = new Empleado(idEmpleado, categoria, nombre, apellido, apodo, usuario, contrasena);
			empleados.add(empleado);
		}

		resultados.close();
		st.close();
		return empleados;
	}

	/**
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void insertarTalleres(Connection c) throws SQLException {

		Statement st = c.createStatement();

		ArrayList<String> queryT = new ArrayList<>();
		// Lista de sentencias SQL para dar de alta a los talleres
		queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Milán', 'diseño');");
		queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('París', 'diseño');");
		queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Madrid', 'costura');");
		queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Nueva York', 'costura');");
		queryT.add("INSERT INTO Taller (nombre_sala, tipo_sala) VALUES ('Berlín', 'pruebas');");

		for (String q : queryT) {
			st.executeUpdate(q);
		}
		st.close();
	}

	/**
	 * 
	 * @param c
	 * @return
	 */
	public ArrayList<Taller> recogeTalleres(Connection c) {
		ArrayList<Taller> talleres = new ArrayList<>();
		Statement st;
		try {
			st = c.createStatement();
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
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		return talleres;
	}

	/**
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void insertarTrajes(Connection c) throws SQLException {

		Statement st = c.createStatement();

		ArrayList<String> queryT = new ArrayList<>();
		// Lista de sentencias SQL para dar de alta a los trajes
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

	/**
	 * 
	 * @param c
	 * @return
	 * @throws SQLException
	 */
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
	
	/**
	 * 
	 * @param c
	 * @param idCliente
	 * @return
	 */
	public ArrayList<Traje> getTrajesPorCliente(Connection c, int idCliente) {
	    ArrayList<Traje> trajes = new ArrayList<>();

	    String query = "SELECT * FROM Traje WHERE id_cliente = ?";

	    try (PreparedStatement pstmt = c.prepareStatement(query)) {

	        pstmt.setInt(1, idCliente);
	        ResultSet rs = pstmt.executeQuery();

	        while (rs.next()) {
	            int idTraje = rs.getInt("id_traje");
	            String nombre = rs.getString("nombre_traje");
	            String estado = rs.getString("estado");
	            int idCli = rs.getInt("id_cliente");

	            Traje t = new Traje(idTraje, nombre, estado, idCli);
	            trajes.add(t);
	        }

	    } catch (SQLException e) {
	        e.printStackTrace();
	    }

	    return trajes;
	}

	/**
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void insertarCitas(Connection c) throws SQLException {

		Statement st = c.createStatement();

		ArrayList<String> queryCi = new ArrayList<>();

		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '09:00', 1, 1, 3, 4, 1);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '10:00', 1, 2, 1, 3, 3);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '11:00', 1, 3, 3, 4, 5);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '12:00', 1, 4, 5, 7, 7);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '15:00', 1, 5, 1, 8, 9);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '16:00', 1, 8, 4, 8, 15);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-13', '17:00', 1, 9, 4, 8, 17);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '09:00', 1, 6, 5, 3, 11);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '10:00', 1, 7, 3, 7, 13);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '11:00', 1, 11, 5, 7, 21);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '12:00', 1, 12, 3, 9, 24);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '15:00', 1, 10, 1, 8, 19);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-14', '16:00', 1, 16, 4, 8, 31);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '09:00', 1, 13, 3, 7, 26);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '10:00', 1, 14, 5, 7, 27);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '11:00', 1, 15, 4, 10, 30);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '15:00', 1, 5, 1, 8, 10);");
		queryCi.add("INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('2026-05-15', '16:00', 1, 8, 4, 8, 16);");
		for (String q : queryCi) {
			st.executeUpdate(q);
		}
		st.close();
	}

	/**
	 * 
	 * @param c
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Cita> recogeCitas(Connection c) throws SQLException {
		ArrayList<Cita> citas = new ArrayList<>();
		Statement st = c.createStatement();
		ResultSet resultados = st.executeQuery("SELECT * FROM Citas ORDER BY fecha, hora_inicio");

		while (resultados.next()) {
			// Extraemos el ID que MySQL creó automáticamente
			int id = resultados.getInt("id_cita");
			Date fecha = resultados.getDate("fecha");
			Time hora = resultados.getTime("hora_inicio");
			int duracion = resultados.getInt("duracion");
			int idEmpleado = resultados.getInt("id_empleado");
			int idCliente = resultados.getInt("id_cliente");
			int idSala = resultados.getInt("id_sala");
			int idTraje = resultados.getInt("id_traje");

			// Se lo pasamos al constructor de tu clase Cita
			Cita cita = new Cita(id, fecha, hora, duracion, idEmpleado, idCliente, idSala, idTraje);
			citas.add(cita);
		}

		resultados.close();
		st.close();
		return citas;
	}

	/**
	 * 
	 * @param c
	 * @throws SQLException
	 */
	public void insertarCitasAprendiz(Connection c) throws SQLException {

		Statement st = c.createStatement();

		ArrayList<String> QueryCA = new ArrayList<>();
		// Lista de sentencias SQL para dar de alta a las citas de los aprendices
		QueryCA.add("INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (1, 1);");
		QueryCA.add("INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (2, 1);");
		QueryCA.add("INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (3, 2);");
		QueryCA.add("INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (5, 1);");
		QueryCA.add("INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (6, 2);");
		QueryCA.add("INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (10, 1);");

		for (String q : QueryCA) {
			st.executeUpdate(q);
		}
		st.close();
	}

	/**
	 * 
	 * @param c
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Cita_Aprendiz> recogeCitasAprendiz(Connection c) throws SQLException {
		ArrayList<Cita_Aprendiz> citasAprendiz = new ArrayList<>();
		Statement st = c.createStatement();
		ResultSet resultados = st.executeQuery("SELECT * FROM Cita_Aprendiz");

		while (resultados.next()) {
			// Extraemos el ID que MySQL creó automáticamente
			int idAprendiz = resultados.getInt("id_aprendiz");
			int idCita = resultados.getInt("id_cita");
			int idEmpleado = resultados.getInt("id_empleado");

			// Se lo pasamos al constructor de tu clase Cita
			Cita_Aprendiz citaAprendiz = new Cita_Aprendiz(idAprendiz, idCita, idEmpleado);
			citasAprendiz.add(citaAprendiz);
		}

		resultados.close();
		st.close();
		return citasAprendiz;
	}

	/**
	 * 
	 * @param c
	 * @return
	 * @throws SQLException
	 */
	public ArrayList<Empleado> recogeAprendices(Connection c) throws SQLException {
		ArrayList<Empleado> empleados = new ArrayList<>();
		Statement st = c.createStatement();
		ResultSet resultados = st.executeQuery("SELECT * FROM Empleados WHERE categoria = 'aprendiz'");

		while (resultados.next()) {
			// Declaración de variables según las columnas de la tabla
			int idEmpleado = resultados.getInt("id_empleado");
			String categoria = resultados.getString("categoria");
			String nombre = resultados.getString("nombre");
			String apellido = resultados.getString("apellido");
			String apodo = resultados.getString("apodo");
			String usuario = resultados.getString("usuario");
			String contrasena = resultados.getString("contraseña");

			// Creación del objeto e inserción en la lista
			Empleado aprendices = new Empleado(idEmpleado, categoria, nombre, apellido, apodo, usuario, contrasena);
			empleados.add(aprendices);
		}

		resultados.close();
		st.close();
		return empleados;
	}
	
	//Al crear un nuevo cliente en la ventana NuevoCliente, añadimos la insercion de la nueva fila a la BBDD
	/**
	 * 
	 * @param c
	 * @param nombre
	 * @param tipo
	 * @param superpoder
	 * @param color
	 */
	public void insertarNuevoCliente(Connection c, String nombre, String tipo, String superpoder, String color) {

		Statement st;
		try {
			st = c.createStatement();
			String queryC = ("INSERT INTO Cliente (nombre_personaje, tipo_heroe, superpoder, colores) VALUES ('" +nombre + "', '" +tipo + "', '" +superpoder + "', '" +color + "');");

			st.executeUpdate(queryC);
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param c
	 * @param id_cliente
	 */
	public void eliminarCliente(Connection c, int id_cliente) {
		
	    String query = "DELETE FROM Cliente WHERE id_cliente = ?";
	    
	    
	    try (PreparedStatement pstmt = c.prepareStatement(query)) {
	        
	        pstmt.setInt(1, id_cliente); 
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar el cliente: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	}
	
	/**
	 * 
	 * @param c
	 * @param id_cliente
	 * @param nombre
	 * @param tipo
	 * @param superpoder
	 * @param color
	 */
	public void actualizarCliente(Connection c, int id_cliente, String nombre, String tipo, String superpoder, String color) {
	    String query = "UPDATE Cliente SET nombre_personaje = ?, tipo_heroe = ?, superpoder = ?, colores = ? WHERE id_cliente = ?";
	    
	    try (PreparedStatement pstmt = c.prepareStatement(query)) {
	        
	        pstmt.setString(1, nombre);
	        pstmt.setString(2, tipo);
	        pstmt.setString(3, superpoder);
	        pstmt.setString(4, color);
	        pstmt.setInt(5, id_cliente);
	        
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar el cliente " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	//Al crear un nuevo taller y traje en la ventana NuevoTaller, añadimos la insercion de la nueva fila a la BBDD
	/**
	 * 
	 * @param c
	 * @param t
	 */
	public void insertarNuevoTaller(Connection c, Taller t) {

		Statement st;
		try {
			st = c.createStatement();
			String queryT = "INSERT INTO Taller (nombre_sala, tipo_sala) VALUES (' " + t.getNombre() + "', '"
					+ t.getTipo() + "');";

			st.executeUpdate(queryT);
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param c
	 * @param id_sala
	 */
	public void borrarTaller(Connection c, int id_sala) {
		String queryT = "DELETE FROM Taller WHERE id_sala = ?";

        try (PreparedStatement pstmt = c.prepareStatement(queryT)) {
	        
	        pstmt.setInt(1, id_sala); 
	        pstmt.executeUpdate();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	/**
	 * 
	 * @param c
	 * @param id_sala
	 * @param tn
	 */
	public void ActualizarTaller(Connection c, int id_sala, Taller tn) {
		// Usamos PreparedStatement para que sea más limpio y seguro
	    String query = "UPDATE Taller SET nombre_sala = ?, tipo_sala = ? WHERE id_sala = ?";
	    
	    try (PreparedStatement pstmt = c.prepareStatement(query)) {
	        pstmt.setString(1, tn.getNombre());
	        pstmt.setString(2, tn.getTipo());
	        pstmt.setInt(3, id_sala);
	        
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar el taller: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	//Al crear un nuevo traje en la ventana NuevoCliente, añadimos la insercion de la nueva fila a la BBDD
	/**
	 * 
	 * @param c
	 * @param nombre
	 * @param estado
	 * @param id_cliente
	 */
	public void insertarNuevoTraje(Connection c, String nombre, String estado, int id_cliente) {

		Statement st;
		try {
			st = c.createStatement();
			String queryTr = "INSERT INTO Traje (nombre_traje, estado, id_cliente) VALUES ('" +nombre + "', '" +estado + "', " +id_cliente + ")";

			st.executeUpdate(queryTr);
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param c
	 * @param id_traje
	 */
    public void eliminarTraje(Connection c, int id_traje) {
		
	    String query = "DELETE FROM Traje WHERE id_traje = ?";
	    
	    
	    try (PreparedStatement pstmt = c.prepareStatement(query)) {
	        
	        pstmt.setInt(1, id_traje); 
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar el traje: " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	}
    
    /**
     * 
     * @param c
     * @param id_traje
     * @param nombre
     * @param estado
     */
    public void actualizarTraje(Connection c, int id_traje, String nombre, String estado) {
	    String query = "UPDATE Traje SET nombre_traje = ?, estado = ? WHERE id_traje = ?";
	    
	    try (PreparedStatement pstmt = c.prepareStatement(query)) {
	        
	        pstmt.setString(1, nombre);
	        pstmt.setString(2, estado);
	        pstmt.setInt(3, id_traje);
	        
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar el traje " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	//Al crear una nueva cita ventana NuevaCitaMaestro, añadimos la insercion de la nueva fila a la BBDD
    /**
     * 
     * @param c
     * @param ci
     */
	public void insertarNuevaCita(Connection c, Cita ci) {

		Statement st;
		try {
			st = c.createStatement();
			String queryCi = "INSERT INTO Citas (fecha, hora_inicio, duracion, id_cliente, id_sala, id_empleado, id_traje) VALUES ('" +ci.getFecha() + "', '" +ci.getHora_inicio() + "', " +ci.getDuracion() + ", " +ci.getId_cliente() + ", " +ci.getId_sala() + ", " +ci.getId_empleado() + ", " +ci.getId_traje() + ");";
			

			st.executeUpdate(queryCi);
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}
	
	/**
	 * 
	 * @param c
	 * @param id_cita
	 */
	public void eliminarCita(Connection c, int id_cita) {
	    // Primero eliminar los registros relacionados en cita_aprendiz (FK)
	    String queryAprendiz = "DELETE FROM Cita_Aprendiz WHERE id_cita = ?";
	    try (PreparedStatement pstmt = c.prepareStatement(queryAprendiz)) {
	        pstmt.setInt(1, id_cita);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar aprendices de la cita: " + e.getMessage());
	        e.printStackTrace();
	    }
	    // Luego eliminar la cita
	    String queryCita = "DELETE FROM Citas WHERE id_cita = ?";
	    try (PreparedStatement pstmt = c.prepareStatement(queryCita)) {
	        pstmt.setInt(1, id_cita);
	        pstmt.executeUpdate();
	    } catch (SQLException e) {
	        System.out.println("Error al eliminar la cita: " + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	/**
	 * 
	 * @param c
	 * @param id_cita
	 * @param ci
	 */
	public void actualizarCita(Connection c, int id_cita, Cita ci) {
	    String query = "UPDATE Citas SET fecha = ?, hora_inicio = ?, duracion = ?, id_cliente = ?, id_sala = ?, id_empleado = ?, id_traje = ? WHERE id_cita = ?";
	    
	    try (PreparedStatement pstmt = c.prepareStatement(query)) {
	        
	        pstmt.setDate(1, (Date) ci.getFecha());
	        pstmt.setTime(2, ci.getHora_inicio());
	        pstmt.setInt(3, ci.getDuracion());
	        pstmt.setInt(4, ci.getId_cliente());
	        pstmt.setInt(5, ci.getId_sala());
	        pstmt.setInt(6, ci.getId_empleado());
	        pstmt.setInt(7, ci.getId_traje());
	        pstmt.setInt(8, id_cita);
	        
	        
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar la cita" + e.getMessage());
	        e.printStackTrace();
	    }
	}
	
	//Al asignar la cita a el/los aprendiz/es en la ventana NuevaCitaOficial, añadimos la insercion de la nueva fila a la BBDD
	/**
	 * 
	 * @param c
	 * @param ca
	 */
	public void insertarNuevaCita_Aprendiz(Connection c, Cita_Aprendiz ca) {

		Statement st;
		try {
			st = c.createStatement();
			String queryCa = "INSERT INTO Cita_Aprendiz (id_cita, id_empleado) VALUES (" +ca.getId_cita() + ", " +ca.getId_empleado() + ");";
			

			st.executeUpdate(queryCa);
			st.close();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	} 
	
	/**
	 * 
	 * @param c
	 * @param id_aprendiz
	 * @param ca
	 */
	public void actualizarCitaAprendiz(Connection c, int id_aprendiz, Cita_Aprendiz ca) {
	    String query = "UPDATE Cita_Aprendiz SET id_cita = ?, id_empleado = ? WHERE id_aprendiz = ?";
	    
	    try (PreparedStatement pstmt = c.prepareStatement(query)) {
	        
	        pstmt.setInt(1, ca.getId_cita());
	        pstmt.setInt(2, ca.getId_empleado());
	        pstmt.setInt(3, id_aprendiz);
	        
	        pstmt.executeUpdate();
	        
	    } catch (SQLException e) {
	        System.out.println("Error al actualizar el aprendiz " + e.getMessage());
	        e.printStackTrace();
	    }
	    
	}
}