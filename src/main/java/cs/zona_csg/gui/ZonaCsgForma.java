package cs.zona_csg.gui;

import cs.zona_csg.modelo.Cliente;
import cs.zona_csg.servicio.ClienteServicio;
import cs.zona_csg.servicio.IClienteServicio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;


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
        guardarButton.addActionListener(e -> guardarCliente());
    }


//Inicia el forma de con el panel principal, tamaño y centrado
    private void iniciarForma(){
        setContentPane(panelPrincipal);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(900, 700);
        setLocationRelativeTo(null);
    }
//////////////////////////////////////////////////////////////////////

//Creación de un modelo de tabla con 4 columnas
    private void createUIComponents() {
        // TODO: place custom component creation code here
        this.tablaModeloClientes = new DefaultTableModel(0, 4);
        String[] cabeceros = {"Id", "Nombre", "Apellido", "Membresia"};
        this.tablaModeloClientes.setColumnIdentifiers(cabeceros);
        this.clientesTabla = new JTable(tablaModeloClientes);

        //Vamos a cargar el listado de clientes
        listarClientes();
    }
//////////////////////////////////////////////////////////////////////

    //Método para listar los datos de los clientes en tablas
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

//////////////////////////////////////////////////////////////////////

    private void guardarCliente(){
        if(nombreTexto.getText().equals("")){ //Si no introduce nombre ya que es igual a cadena vacia
            mostrarMensaje("Proporciona un nombre");
            nombreTexto.requestFocusInWindow(); //Para dar foco al campo de texto de nombre ya que queremos que sea obligatorio
            return;
        }
        if(membresiaTexto.getText().equals("")){
            mostrarMensaje("Proporciona un id de membresía");
            membresiaTexto.requestFocusInWindow();
            return;
        }
        // Recuperamos los valores del formulario
        var nombre = nombreTexto.getText();
        var apellido = apellidoTexto.getText();
        var membresia = Integer.parseInt(membresiaTexto.getText());
        var cliente = new Cliente(); //ob
        //El id es un incremento automatico siendo una inserción por lo que lo dejamos como nulo
        cliente.setNombre(nombre);
        cliente.setApellido(apellido);
        cliente.setMembresia(membresia);
        this.clienteServicio.guardarCliente(cliente); //De esta manera se inserta el nuevo cliente en la base de datos
        limpiarFormulario();//Una vez que se limpia el formulario hay que recargar la lista de usuarios
        listarClientes();
    }

private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
}

private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
}



}




