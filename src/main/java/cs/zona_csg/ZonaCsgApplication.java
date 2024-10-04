package cs.zona_csg;

import cs.zona_csg.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

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
			//salir = ejecutarOpciones(sc, opcion);
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
			Elige una opción\s""");
	}
}
