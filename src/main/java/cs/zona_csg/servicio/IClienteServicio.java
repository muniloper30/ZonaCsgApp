package cs.zona_csg.servicio;

import java.util.List;
import cs.zona_csg.modelo.Cliente;

public interface IClienteServicio {
    public List<Cliente> listarClientes(); //MÉTODO QUE VA REGRESAR UNA LISTA DE TIPO CLIENTE (importamos la clase Cliente)

    public Cliente buscarClientePorId(Cliente cliente);// MÉTODO PARA BUSCAR CLIENTE POR ID PASANDOLE UN OBJETO DE TIPO CLIENTE

    public void guardarCliente(Cliente cliente); // MÉTODO TANTO PARA INSERTAR COMO PARA ACTUALIZAR CLIENTES (SI EL VALOR DE LA LLAVE PRIMARIA DEL OBJETO DE TIPO CLIENTE QUE ESTAMOS RECIBIENDO ES NULL SE VA HACER UNA INSERCIÓN , SI ES DIFERENTE DE NULL, SE REALIZARÁ UNA MODIFICACIÓN O UPDATE

    public void eliminarCliente(Cliente cliente); //MÉTODO PARA ELIMINAR UN OBJETO DEL TIPO CLIENTE SEGÚN EL VALOR DE ID
}
