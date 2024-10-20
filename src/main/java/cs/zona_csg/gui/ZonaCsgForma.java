package cs.zona_csg.gui;

import cs.zona_csg.servicio.ClienteServicio;
import cs.zona_csg.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;


@Component
public class ZonaCsgForma extends JFrame{
    private JPanel panelPrincipal;
    IClienteServicio clienteServicio;

    @Autowired  //Inyección de dependencia
    public ZonaCsgForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
    }



    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }



}
