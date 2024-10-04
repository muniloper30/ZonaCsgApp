package cs.zona_csg;

import cs.zona_csg.servicio.IClienteServicio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ZonaCsgApplication implements CommandLineRunner {

	@Autowired
	private IClienteServicio clienteServicio;

	private static final Logger logger = LoggerFactory.getLogger(ZonaCsgApplication.class);//Mandamos información a la consola

	public static void main(String[] args) {

		logger.info("Iniciando la aplicación");



		SpringApplication.run(ZonaCsgApplication.class, args);//Levantamos la fábrica de objetos de spring


		logger.info("Aplicación finalizada");
	}

	@Override
	public void run(String... args) throws Exception {
		logger.info("*** Csg Studio App ***");
	}
}
