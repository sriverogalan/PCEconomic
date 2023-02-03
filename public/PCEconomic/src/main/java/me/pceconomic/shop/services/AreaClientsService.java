package me.pceconomic.shop.services;


import jakarta.servlet.http.HttpSession;
import lombok.Getter;
import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.areaclients.AddDirectionForm;
import me.pceconomic.shop.domain.forms.areaclients.ChangeEmailForm;
import me.pceconomic.shop.domain.forms.areaclients.ChangeNameForm;
import me.pceconomic.shop.domain.forms.areaclients.ChangePasswordForm;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.DireccioRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.ui.Model;

@Service @Getter
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

    public void saveDirection(Client client, AddDirectionForm directionForm) {
        Direccio direccio = new Direccio(directionForm);
        Persona persona = client.getPersona();

        direccioRepository.save(direccio);

        persona.getDireccions().add(direccio);

        clientRepository.save(client);
        personaRepository.save(persona);
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

}
