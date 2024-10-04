package cs.zona_csg.servicio;


import cs.zona_csg.modelo.Cliente;
import cs.zona_csg.repositorio.ClienteRepositorio;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

//AGREGAMOS LA IMPLEMENTACIÓN para que la capad de presentación pueda interactuar con la capa de servicio , esta a su vez con la capa de datos, datos con la de entidad y finalmente con la base de datos
@Service // Notación necesaria para usar spring y utilizar la injección de dependencias de esta clase de servicio
public class ClienteServicio implements IClienteServicio{

    //Realizamos una inyección de dependencias @Repository para poder comunicarnos con la base de datos
    @Autowired
    private ClienteRepositorio clienteRepositorio;

    @Override
    public List<Cliente> listarClientes() {
        List<Cliente> clientes = clienteRepositorio.findAll();//Método de clienteRepositorio gracias a que fue extendido y  definido dentro de la interface de JpaRepository
        return clientes;
    }

    @Override
    public Cliente buscarClientePorId(Integer idCliente) {
        //En caso de que el cliente que estamos buscando, no tengamos ningun registro en la base de datos, devuelve null, de lo contrario se devuelve el objeto de tipo cliente que se encuentre en la base de datos
        Cliente cliente = clienteRepositorio.findById(idCliente).orElse(null);
        return cliente;
    }

    @Override
    public void guardarCliente(Cliente cliente) {
        clienteRepositorio.save(cliente); //De manera interna, si el id es igual a null se hace un insert de lo contrario, se hace un update gracias a jpa
    }

    @Override
    public void eliminarCliente(Cliente cliente) {
        clienteRepositorio.delete(cliente); //Se toma el id del objeto de tipo cliente y si se encuentra se elimina
    }
}
