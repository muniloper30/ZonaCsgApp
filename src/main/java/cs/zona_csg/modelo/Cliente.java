package cs.zona_csg.modelo;

//Clase de entidad
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.*;

@Entity
@Data //Notación para generar todos los métodos get y set de todos los atributos privados de nuestra clase
@NoArgsConstructor //Notación para constructor vacio a nuestra clase
@AllArgsConstructor //Notación para agregar constructor con todos los argumentos
@ToString //Notación para agregar el método toString()
@EqualsAndHashCode //Notación para agregar los métodos equals() y hashCode()
public class Cliente {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String nombre;
    private String apellido;
    private Integer membresia;
}
