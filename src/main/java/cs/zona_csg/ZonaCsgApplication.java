package cs.zona_csg;

import cs.zona_csg.modelo.Cliente;
import cs.zona_csg.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.util.List;
import java.util.Scanner;

@SpringBootApplication
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
			case 6 -> {
				logger.info("--- SALIENDO DEL SISTEMA CSG STUDIO ---");
				salir = true;
			}
		}
		return salir;
	}
}
 