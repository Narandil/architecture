package com.wgt.imt.architecture.business.clients;

import com.wgt.imt.architecture.business.clients.model.Client;
import com.wgt.imt.architecture.business.clients.validators.ClientAlreadyExistValidatorStep;
import com.wgt.imt.architecture.business.clients.validators.ClientGenreValidatorStep;
import com.wgt.imt.architecture.business.common.validators.ConstraintValidatorStep;
import com.wgt.imt.architecture.infrastructures.bdd.clients.ClientsBddService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientsServiceValidator extends ClientsService {

    public ClientsServiceValidator(final ClientsBddService service) {
        super(service);
    }

    public Client create(final Client newClient) {
        new ConstraintValidatorStep<Client>()
                .linkWith(new ClientAlreadyExistValidatorStep(this.service))
                .linkWith(new ClientGenreValidatorStep())
                .validate(newClient)
                .throwIfInvalid();

        return super.create(newClient);
    }

    public void update(final Client updatedClient) {
        new ConstraintValidatorStep<Client>()
                .linkWith(new ClientGenreValidatorStep())
                .validate(updatedClient)
                .throwIfInvalid();

        super.update(updatedClient);
    }
}
