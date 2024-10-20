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
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;


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
    private Integer idCliente;

    @Autowired  //Inyección de dependencia
    public ZonaCsgForma(ClienteServicio clienteServicio){
        this.clienteServicio = clienteServicio;
        iniciarForma();
        guardarButton.addActionListener(e -> guardarCliente());
        clientesTabla.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent e) {
                super.mouseClicked(e);
                cargarClienteSeleccionado();
            }
        });
        eliminarButton.addActionListener(e -> eliminarCliente());
        limpiarButton.addActionListener(e -> limpiarFormulario());
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
        //this.tablaModeloClientes = new DefaultTableModel(0, 4);
        this.tablaModeloClientes = new DefaultTableModel(0,4){
            @Override
            public boolean isCellEditable(int row, int column){
                return false;
            }
        };

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

//Método que añade un nuevo cliente
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
        var cliente = new Cliente(this.idCliente, nombre, apellido, membresia);

        if (this.idCliente == null){
            this.clienteServicio.guardarCliente(cliente); //INSERTAR O MODIFICAR (Dependiendo de si el id proporcionado)
            mostrarMensaje("Se agregó un nuevo usuario");
        }else if (this.idCliente != null){
            String confirmacion = JOptionPane.showInputDialog("¿Está seguro de modificar el cliente selccionado?" + "\n" + "Presione S para confirmar");
            if ("s".equalsIgnoreCase(confirmacion)) {
                this.clienteServicio.guardarCliente(cliente); //INSERTAR O MODIFICAR (Dependiendo de si el id proporcionado)
                mostrarMensaje("Se actualizó el cliente seleccionado");
            }else {
                mostrarMensaje("Se ha cancelado la operación");
            }
        }
        limpiarFormulario();//Una vez que se limpia el formulario hay que recargar la lista de usuarios
        listarClientes();
    }
/////////////////////////////////////////////////////////////////////
    private void eliminarCliente(){
        var renglon = clientesTabla.getSelectedRow();
        if (renglon != -1){
            var idClienteStr = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(idClienteStr);
            var cliente = new Cliente();
            cliente.setId(this.idCliente);
            String confirmacion = JOptionPane.showInputDialog("¿Está seguro de eliminar al cliente seleccionado?" + "\n" + "Presione S para confirmar");
            if ("s".equalsIgnoreCase(confirmacion)){
                clienteServicio.eliminarCliente(cliente);
                mostrarMensaje("Cliente con id " + this.idCliente + " ha sido eliminado correctamente");
            }else
                mostrarMensaje("Se ha cancelado la operación");
            listarClientes();
        }
        else
            mostrarMensaje("Debe seleccionar un Cliente para poder eliminarlo");
    }

    //////////////////////////////////////////////////////////////////////

//Hace que se selccione un cliente para poder modificarlo si se desea en el menú de datos de usuario a la izquierda
    private void cargarClienteSeleccionado(){
        var renglon = clientesTabla.getSelectedRow(); //Con este método nos va seleccionar el registro que se halla seleccionado
        if (renglon != -1){ //-1 significa que no se ha seleccionado ningun registro
            var id = clientesTabla.getModel().getValueAt(renglon, 0).toString();
            this.idCliente = Integer.parseInt(id);
            var nombre = clientesTabla.getModel().getValueAt(renglon, 1).toString();
            this.nombreTexto.setText(nombre);
            var apellido = clientesTabla.getModel().getValueAt(renglon, 2).toString();
            this.apellidoTexto.setText(apellido);
            var membresia = clientesTabla.getModel().getValueAt(renglon, 3).toString();
            this.membresiaTexto.setText(membresia);
        }
    }


    //////////////////////////////////////////////////////////////////////

private void mostrarMensaje(String mensaje){
        JOptionPane.showMessageDialog(this, mensaje);
}
//////////////////////////////////////////////////////////////////////

//Método para limpiarFormulario antes de volver a rellenar
private void limpiarFormulario(){
        nombreTexto.setText("");
        apellidoTexto.setText("");
        membresiaTexto.setText("");
        //Limpiamos el id del cliente selecionado
        this.idCliente = null;

        //Deseleccionamos el registro seleccionado de la tabla
        this.clientesTabla.getSelectionModel().clearSelection();
}



}




