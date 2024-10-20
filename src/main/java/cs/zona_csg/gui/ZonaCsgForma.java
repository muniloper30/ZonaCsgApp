package cs.zona_csg.gui;

import cs.zona_csg.servicio.ClienteServicio;
import cs.zona_csg.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;


@Component
public class ZonaCsgForma extends JFrame{
    private JPanel panelPrincipal;
    private JTable clientesTabla;
    private JTextField nombreTexto;
    private JTextField apellidoTexto;
    private JTextField membresiaTexto;
    private JButton guardarButton;
    private JButton eliminarButton;
    private JButton limpiarButton;
    IClienteServicio clienteServicio;
    private DefaultTableModel tablaModeloClientes;

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


    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0, 4);
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);

        //Vamos a cargar el listado de clientes
        listarClientes();
    }

    private void listarClientes(){
        this.tablaModeloClientes.setRowCount(0);//El conteo de registros empieza en 0
        var clientes = this.clienteServicio.listarClientes(); //Vamos a recibir el listado de clientes directamente en la variable creada
        clientes.forEach(cliente -> {
            Object[] renglonCliente = { //Creamos un renglon por cada cliente con sus datos correspondientes
                    cliente.getId(),
                    cliente.getNombre(),
                    cliente.getApellido(),
                    cliente.getMembresia()
            };
            this.tablaModeloClientes.addRow(renglonCliente); //Por cada objeto que tengamos lo vamos a ir agregando a nuestra tabla de modelo clientes
        });
    }
}
