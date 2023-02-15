package me.pceconomic.shop.services;


import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.areaclients.*;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.DireccioRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

import java.util.Set;

@Service
@Getter
public class AreaClientsService {

    private final ClientRepository clientRepository;
    private final PersonaRepository personaRepository;
    private final DireccioRepository direccioRepository;
    private final PasswordEncoder passwordEncoder;

    @Autowired
    public AreaClientsService(PasswordEncoder passwordEncoder, PersonaRepository personaRepository, DireccioRepository direccioRepository, ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.personaRepository = personaRepository;
        this.direccioRepository = direccioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public void sendToModel(Model model, HttpSession session) {
        model.addAttribute("user", session.getAttribute("persona"));
        model.addAttribute("changeName", new ChangeNameForm());
        model.addAttribute("directionForm", new AddDirectionForm());
        model.addAttribute("changePasswordForm", new ChangePasswordForm());
        model.addAttribute("changeEmailForm", new ChangeEmailForm());
    }

    public void saveDirection(Client client, AddDirectionForm directionForm, HttpSession session) {
        Direccio direccio = new Direccio(directionForm);
        Persona persona = client.getPersona();

        setDireccioPrincipal(direccio, persona.getDireccions());
        direccioRepository.save(direccio);

        persona.getDireccions().add(direccio);

        clientRepository.save(client);
        personaRepository.save(persona);
        this.resetSession(client.getId(), session);
    }

    public void deleteDirection(Client client, int id, HttpSession session) {
        Persona persona = client.getPersona();
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
        this.resetSession(client.getId(), session);
    }

    public void updateDirection(Client client, AddDirectionForm directionForm, int id, HttpSession session) {
        Persona persona = client.getPersona();
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
        this.resetSession(client.getId(), session);
    }

    public void changeName(Client client, ChangeNameForm changeNameForm) {
        Persona persona = client.getPersona();
        persona.setName(changeNameForm.getNewName());
        personaRepository.save(persona);
    }

    public void changePassword(Client client, ChangePasswordForm changePasswordForm) {
        Persona persona = client.getPersona();

        String encodedPassword = passwordEncoder.encode(changePasswordForm.getNewPassword());

        persona.setPassword(encodedPassword);
        personaRepository.save(persona);
    }

    public void changeEmail(Client client, ChangeEmailForm changeEmailForm) {
        if (personaRepository.existsByEmail(changeEmailForm.getNewEmail())) {
            throw new IllegalArgumentException();
        }

        Persona persona = client.getPersona();

        persona.setEmail(changeEmailForm.getNewEmail());
        personaRepository.save(persona);
    }

    public void changeTelephone(Client client, ChangeTelephoneForm changeTelephoneForm) {
        if (personaRepository.existsByTelefon(changeTelephoneForm.getNewTelephone())) {
            throw new IllegalArgumentException();
        }

        Persona persona = client.getPersona();

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
        Client client = clientRepository.findById(id).orElse(null);

        if (client == null) {
            throw new IllegalArgumentException();
        }

        Set<Direccio> direccions = client.getPersona().getDireccions();

        session.setAttribute("persona", client);
        session.setAttribute("direccions", direccions);
    }
}
