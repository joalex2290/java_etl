/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package DATA;

/**
 *
 * @author John A. Munoz
 */
public class Parada {

    private String Id;
    private String Nombre;
    private String Tipo;

    public Parada(String Id, String Nombre, String Tipo) {

        this.Id = Id;
        this.Nombre = Nombre;
        this.Tipo = Tipo;

    }

    public String getId() {
        return Id;
    }

    public void setId(String Id) {
        this.Id = Id;
    }

    public String getNombre() {
        return Nombre;
    }

    public void setNombre(String Nombre) {
        this.Nombre = Nombre;
    }

    public String getTipo() {
        return Tipo;
    }

    public void setTipo(String Tipo) {
        this.Tipo = Tipo;
    }

}
