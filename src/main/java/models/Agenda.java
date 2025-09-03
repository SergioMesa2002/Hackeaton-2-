package models;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class Agenda {

    private final List<Contact> contacts;
    private final int maxQuantity;

    public Agenda(int quantity) {
        this.maxQuantity = quantity;
        this.contacts = new ArrayList<>(maxQuantity);
    }

    public String addContact(Contact contact) throws Exception {
        exepciones(contact);
        agendaLlena();

        if (contacts.size() < maxQuantity  && !existeContacto(contact)){
            contacts.add(contact);
            return contact.getName() +  " " + contact.getLastname()+ " agregado a la lista";
        }else {
            return "Contacto no agregado";
        }
    }

    public void exepciones(Contact contact) throws Exception {
        if (contact.getName().isBlank()){
            throw new Exception("El nombre no puede ser vacío");
        }
        if (contact.getLastname().isBlank()){
            throw new Exception("El apellido no puede ser vacío");
        }

        if (contact.getPhone().isBlank() || contact.getPhone() == null){
            throw new Exception("El teléfono no puede ser vacío");
        }
    }

    public boolean existeContacto(Contact contact){
        return contacts.stream().anyMatch(c -> c.getName().equalsIgnoreCase(contact.getName()) && c.getLastname().equalsIgnoreCase(contact.getLastname()));
    }

    public Optional<Contact> buscarContacto(String nombre, String apellido){
        return contacts.stream().filter(c -> c.getName().equalsIgnoreCase(nombre) && c.getLastname().equalsIgnoreCase(apellido)).findFirst();
    }

    public List<Contact> listarContactos(){
        List<Contact> v2 = contacts;
        v2.sort((a, b) -> {
            int cmpName = a.getName().compareToIgnoreCase(b.getName());
            if (cmpName != 0) return cmpName;
            return a.getLastname().compareToIgnoreCase(b.getLastname());
        });
        return v2;
    }

    public void eliminarContacto(String nombre, String apellido){
        contacts.removeIf(c-> c.getName().equalsIgnoreCase(nombre) && c.getLastname().equalsIgnoreCase(apellido));
    }

    public void modificarTelefono(String nombre, String apellido, String telefono){
        Optional<Contact> contact = buscarContacto(nombre,apellido);
        if (contact.isPresent()){
            Contact c = contact.get();
            c.setPhone(telefono);
        }else{
            System.out.println("no existe el contacto");
        }
    }

    // Ver si la agenda está llena
    public void agendaLlena() throws Exception {
        if (contacts.size()>= maxQuantity){
            throw new Exception("La lista está llena");
        }
    }

    // Espacios libres
    public int espaciosLibres() {
        return maxQuantity - contacts.size();
    }




}
