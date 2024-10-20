package cs.zona_csg;

import cs.zona_csg.modelo.Cliente;
import cs.zona_csg.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.security.auth.login.LoginContext;
import java.util.List;
import java.util.Scanner;

//@SpringBootApplication //No se levanta la app por esta clase al comentarla la desactivamos
public class ZonaCsgApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaCsgApplication.class);//Mandamos información a la consola

	String nl = System.lineSeparator(); //Variable para generar e imprimir un salto de línea

	public static void main(String[] args) {

		logger.info("Iniciando la aplicación");



		SpringApplication.run(ZonaCsgApplication.class, args);//Levantamos la fábrica de objetos de spring


		logger.info("Aplicación finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		zonaZsgApp();
	}

	public void zonaZsgApp(){
		var salir = false;
		var sc = new Scanner(System.in);
		while (!salir){
			var opcion = mostrarMenu(sc);
			salir = ejecutarOpciones(sc, opcion);
			logger.info(nl); //CADENA VACIA
		}
	}

	private int mostrarMenu(Scanner sc){
		logger.info("""
			\n*** Csg Studio App ***
			1.Listar Clientes
			2.Buscar Clientes
			3.Agregar Clientes
			4.Modificar Cliente
			5.Eliminar Cliente
			6.Salir
			Elige una opción:\s""");
		return Integer.parseInt(sc.nextLine());
	}

	private boolean ejecutarOpciones(Scanner sc, int opcion){
		var salir = false;
		switch (opcion){
			case 1 -> {
				logger.info(nl + "--- LISTADO DE CLIENTES ---" +nl);
				List<Cliente> clientes = clienteServicio.listarClientes();
				clientes.forEach(cliente -> logger.info(cliente.toString()+ nl));
			}
			case 2 -> {
				logger.info(nl + "--- BUSCAR CLIENTE POR ID ---" +nl);
				logger.info("Introduce ID cliente a buscar: ");
				var idCliente = Integer.parseInt(sc.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					logger.info("Cliente encontrado: " + cliente + nl);
				}else {
					logger.info("Cliente no encotrado: " + cliente + nl);
				}
			}
			case 3 -> {
				logger.info("--- AGREGAR NUEVO CLIENTE ---" +nl);
				logger.info("Nombre: ");
				var nombre = sc.nextLine();
				logger.info("Apellido: ");
				var apellido = sc.nextLine();
				logger.info("Membresia: ");
				var membresia = Integer.parseInt(sc.nextLine());
				var cliente = new Cliente();
				cliente.setNombre(nombre);
				cliente.setApellido(apellido);
				cliente.setMembresia(membresia);
				clienteServicio.guardarCliente(cliente);
				logger.info("Cliente agregado: " + cliente + nl);
			}
			case 4 -> {
				logger.info("--- MODIFICAR CLIENTE ---" + nl);
				logger.info("Introduce id de cliente a modificar: " );
				var idCliente = Integer.parseInt(sc.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					logger.info("Nombre: ");
					var nombre = sc.nextLine();
					logger.info("Apellido: ");
					var apellido = sc.nextLine();
					logger.info("Membresia: ");
					var membresia = Integer.parseInt(sc.nextLine());
					cliente.setNombre(nombre);
					cliente.setApellido(apellido);
					cliente.setMembresia(membresia);
					//Validación para modificar al cliente
					logger.info("¿Está seguro de que desea modificar al siguiente cliente?  " + cliente + nl);
					logger.info("Escriba 'S' para confirmar o cualquier otro caracter para cancelar: ");
					String confirmacion = sc.nextLine().trim();
					//Validamos la respuesta del usuario
					if ("s".equalsIgnoreCase(confirmacion)){
						clienteServicio.guardarCliente(cliente);
						logger.info("Cliente modificado: " + cliente + nl);
					}else {
						logger.info("Operación cancelada, cliente no modificado " +nl);
					}
				}else {
					logger.info("Cliente no encontrado: " +cliente + nl);
				}
			}
			case 5 -> {
				logger.info("--- ELIMINAR CLIENTE ---" + nl);
				logger.info("Introduce id de cliente a eliminar: ");
				var idCliente = Integer.parseInt(sc.nextLine());
				Cliente cliente = clienteServicio.buscarClientePorId(idCliente);
				if (cliente != null){
					//Mostramos detalles del cliente y verificamos si de verdad deseamos borrar a ese cliente
					logger.info("¿Está seguro de que desea eliminar al siguiente cliente?  " + cliente + nl);
					logger.info("Escriba 'S' para confirmar o cualquier otro caracter para cancelar: ");
					String confirmacion = sc.nextLine().trim();

					//Validamos respuesta del usuario
					if ("s".equalsIgnoreCase(confirmacion)){ //ignorando mayusculas o minúsculas
						logger.info("Cliente elinimado correctamente: " +cliente + nl);
						clienteServicio.eliminarCliente(cliente);
					}else {
						logger.info("Operación cancelada, El cliente no fué eliminado. " + nl);
					}
				}else {
					logger.info("ID no encontrado, cliente no eliminado: " +cliente + nl);
				}
			}
			case 6 -> {
				logger.info("--- SALIENDO DEL SISTEMA CSG STUDIO ---" +nl + nl);
				salir = true;
			}
			default -> logger.info("Opción no válida: " + opcion);
		}
		return salir;
	}
}
 