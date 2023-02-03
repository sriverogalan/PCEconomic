package me.pceconomic.shop.services;


import me.pceconomic.shop.domain.entities.persona.Client;
import me.pceconomic.shop.domain.entities.persona.Direccio;
import me.pceconomic.shop.domain.entities.persona.Persona;
import me.pceconomic.shop.domain.forms.AddDirectionForm;
import me.pceconomic.shop.domain.forms.ChangeNameForm;
import me.pceconomic.shop.repositories.ClientRepository;
import me.pceconomic.shop.repositories.DireccioRepository;
import me.pceconomic.shop.repositories.PersonaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AreaClientsService {

    private final ClientRepository clientRepository;
    private final PersonaRepository personaRepository;
    private final DireccioRepository direccioRepository;

    @Autowired
    public AreaClientsService(PersonaRepository personaRepository, DireccioRepository direccioRepository, ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
        this.personaRepository = personaRepository;
        this.direccioRepository = direccioRepository;
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

}
