package me.pceconomic.shop.services;


import jakarta.servlet.http.HttpSession;
import jakarta.transaction.Transactional;
import lombok.Getter;
import me.pceconomic.shop.domain.entities.article.factura.Factura;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.areaclients.*;
import me.pceconomic.shop.repositories.DireccioRepository;
import me.pceconomic.shop.repositories.FacturaRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Set;

@Service
@Getter
public class AreaClientsService {

    private final PersonaRepository personaRepository;
    private final DireccioRepository direccioRepository;
    private final PasswordEncoder passwordEncoder;
    private final FacturaRepository facturaRepository;

    @Autowired
    public AreaClientsService(PasswordEncoder passwordEncoder, PersonaRepository personaRepository, DireccioRepository direccioRepository, FacturaRepository facturaRepository) {
        this.personaRepository = personaRepository;
        this.direccioRepository = direccioRepository;
        this.passwordEncoder = passwordEncoder;
        this.facturaRepository = facturaRepository;
    }

    public void sendToModel(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("persona"));
        model.addAttribute("changeName", new ChangeNameForm());
        model.addAttribute("directionForm", new AddDirectionForm());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        model.addAttribute("changeEmailForm", new ChangeEmailForm());
    }

    @Transactional
    public Factura getFacturaWithLineasFacturas(int id) {
        Factura factura = facturaRepository.findById(id).orElse(null);
        if (factura != null) {
            Hibernate.initialize(factura.getLineasFacturas());
        }
        return factura;
    }

    public void saveDirection(Persona persona, AddDirectionForm directionForm, HttpSession session) {
        Direccio direccio = new Direccio(directionForm);

        setDireccioPrincipal(direccio, persona.getDireccions());
        direccioRepository.save(direccio);

        persona.getDireccions().add(direccio);

        personaRepository.save(persona);
        this.resetSession(persona.getId(), session);
    }

    public void deleteDirection(Persona persona, int id, HttpSession session) {
        Set<Direccio> direccions = persona.getDireccions();

        Direccio direccio = direccioRepository.findById(id).orElse(null);

        persona.getDireccions().remove(direccio);
        personaRepository.save(persona);

        for (int i = 0; i < direccions.size(); i++) {
            Direccio d = (Direccio) direccions.toArray()[i];
            if (d.getId() == id) {
                direccions.remove(d);
                direccioRepository.delete(d);
                break;
            }
        }

        persona.setDireccions(direccions);
        personaRepository.save(persona);
        this.resetSession(persona.getId(), session);
    }

    public void updateDirection(Persona persona, AddDirectionForm directionForm, int id, HttpSession session) {
        Direccio direccio = direccioRepository.findById(id).orElse(null);
        System.out.println("Form: " + directionForm);
        System.out.println("Direccio: " + direccio);
        if (direccio == null) {
            throw new IllegalArgumentException();
        }

        direccio.setFullName(directionForm.getNombre());
        direccio.setPhone(directionForm.getTelefono());
        direccio.setStreetandnumber(directionForm.getCalle());
        direccio.setCity(directionForm.getCiudad());
        direccio.setProvince(directionForm.getProvincia());
        direccio.setPostalCode(directionForm.getCodigoPostal());
        direccio.setCountry(directionForm.getPais());
        direccio.setPrincipal(Boolean.parseBoolean(directionForm.getPrincipal()));

        direccioRepository.save(direccio);
        System.out.println("Direccio Editada: " + direccio);
        this.resetSession(persona.getId(), session);
    }

    public void changeName(Persona persona, ChangeNameForm changeNameForm) {
        persona.setName(changeNameForm.getNewName());
        personaRepository.save(persona);
    }

    public void changePassword(Persona persona, ChangePasswordForm changePasswordForm) {
        String encodedPassword = passwordEncoder.encode(changePasswordForm.getNewPassword());

        persona.setPassword(encodedPassword);
        personaRepository.save(persona);
    }

    public void changeEmail(Persona persona, ChangeEmailForm changeEmailForm) {
        if (personaRepository.existsByEmail(changeEmailForm.getNewEmail())) {
            throw new IllegalArgumentException();
        }

        persona.setEmail(changeEmailForm.getNewEmail());
        personaRepository.save(persona);
    }

    public void changeTelephone(Persona persona, ChangeTelephoneForm changeTelephoneForm) {
        if (personaRepository.existsByTelefon(changeTelephoneForm.getNewTelephone())) {
            throw new IllegalArgumentException();
        }

        persona.setTelefon(changeTelephoneForm.getNewTelephone());
        personaRepository.save(persona);
    }

    public void setDireccioPrincipal(Direccio direccio, Set<Direccio> direccions) {
        for (Direccio d : direccions) {
            d.setPrincipal(false);
            direccioRepository.save(d);
        }

        direccio.setPrincipal(true);
    }

    public void resetSession(int id, HttpSession session) {
        Persona persona = personaRepository.findById(id).orElse(null);

        if (persona == null) {
            throw new IllegalArgumentException();
        }

        Set<Direccio> direccions = persona.getDireccions();

        session.setAttribute("persona", persona);
        session.setAttribute("direccions", direccions);
    }
}
