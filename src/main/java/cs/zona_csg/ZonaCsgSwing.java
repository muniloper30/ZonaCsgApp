package cs.zona_csg;

import com.formdev.flatlaf.FlatDarculaLaf;
import cs.zona_csg.gui.ZonaCsgForma;
import org.springframework.boot.WebApplicationType;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;

import javax.swing.*;



@SpringBootApplication //NOTACIÓN PARA INDICAR QUE ES NUESTRA CLASE PRINCIPAL

public class ZonaCsgSwing {
    public static void main(String[] args) {
        //Configuramos el modo oscuro
        FlatDarculaLaf.setup();
        // Instanciamos la fábrica de Spring
        ConfigurableApplicationContext contextoSpring =
                new SpringApplicationBuilder(ZonaCsgSwing.class)//Aplicación de escritorio y no web
                        .headless(false)
                        .web(WebApplicationType.NONE)
                        .run(args);

        //Crear un objeto de Swing

        SwingUtilities.invokeLater(() -> { //Ejecutamos nuestra app swing de manera indirecta
            ZonaCsgForma zonaCsgForma = contextoSpring.getBean(ZonaCsgForma.class);
            zonaCsgForma.setVisible(true);
        });
    }
}
