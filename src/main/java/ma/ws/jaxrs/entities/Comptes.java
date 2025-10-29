package ma.ws.jaxrs.entities;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.util.ArrayList;
import java.util.List;

@XmlRootElement(name = "comptes")
public class Comptes {
    private List<Compte> items = new ArrayList<>();

    public Comptes() {}
    public Comptes(List<Compte> items) { this.items = items; }

    @XmlElement(name = "compte")
    public List<Compte> getItems() { return items; }
    public void setItems(List<Compte> items) { this.items = items; }
}
