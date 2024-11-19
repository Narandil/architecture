package com.imt.td.architecture.business.clients;

import com.imt.td.architecture.business.clients.model.Client;
import com.imt.td.architecture.common.model.GenreEnum;
import com.imt.td.architecture.infrastructures.bdd.clients.ClientsBddService;
import com.imt.td.architecture.interfaces.rest.common.exception.BadRequestException;
import com.imt.td.architecture.interfaces.rest.common.exception.ConflictException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.Optional;
import java.util.UUID;

class ClientsServiceTest {
    protected static final UUID IDENTIFIER = UUID.randomUUID();
    protected static final Client MOCK = Client.builder().identifier(IDENTIFIER).genre(GenreEnum.HOMME).firstname("Emmanuel").lastname("Waguet").comptes(Collections.emptySet()).build();
    
    protected final ClientsBddService bddService = Mockito.mock(ClientsBddService.class);
    
    @Nested
    class GetAllTests{
        
        @Test
        void nullTest() {
            Mockito.when(bddService.getAll()).thenReturn(null);
            Assertions.assertEquals(
                    Collections.emptySet(),
                    Assertions.assertDoesNotThrow(
                            () -> new ClientsService(bddService).getAll()
                    )
            );
        }
        
        @Test
        void onlyOneResultTest(){
            Mockito.when(bddService.getAll()).thenReturn(Collections.singleton(MOCK));
            
            Assertions.assertEquals(
                    Collections.singleton(MOCK),
                    Assertions.assertDoesNotThrow(
                            () -> new ClientsService(bddService).getAll()
                    )
            );
        }
    }
    
    @Nested
    class GetOneTests {
        
        @Test
        void emptyTest(){
            Mockito.when(bddService.get(ArgumentMatchers.any())).thenReturn(Optional.empty());
            Assertions.assertEquals(
                    Optional.empty(),
                    new ClientsService(bddService).getOne(UUID.randomUUID())
            );
        }
        
        @Test
        void nominalTest(){
            final UUID identifier = UUID.randomUUID();
            Mockito.when(bddService.get(ArgumentMatchers.any())).thenReturn(Optional.of(MOCK));
            Assertions.assertEquals(
                    Optional.of(MOCK),
                    new ClientsService(bddService).getOne(UUID.randomUUID())
            );
        }
    }
    
    @Nested
    class CreateTests {
        
        @Test
        void nullTest(){
            Assertions.assertEquals(
                    "Impossible de valider un objet nul",
                    Assertions.assertThrows(
                            NullPointerException.class,
                            () -> new ClientsService(bddService).create(null)
                    ).getMessage()
            );
        }
        
        @Test
        void alreadyExistTest(){
            Mockito.when(bddService.getAll()).thenReturn(Collections.singleton(MOCK));
            Assertions.assertEquals(
                    "Un client ayant ses infos existe déjà : lastname : " + MOCK.getLastname() + ", firstname : " + MOCK.getFirstname()+", genre : " + MOCK.getGenre(),
                    Assertions.assertThrows(
                            ConflictException.class,
                            () -> new ClientsService(bddService).create(MOCK)
                    ).getMessage()
            );
        }
        
        @Test
        void genreValidatorTest(){
            Mockito.when(bddService.getAll()).thenReturn(Collections.emptySet());
            Assertions.assertEquals(
                    "Le genre communiqué ne correspond pas aux valeurs acceptables : " + GenreEnum.ACCEPTABLE_VALUES,
                    Assertions.assertThrows(
                            BadRequestException.class,
                            () -> new ClientsService(bddService).create(MOCK.toBuilder().genre(GenreEnum.INCONNU).build())
                    ).getMessage()
            );
        }
    }
}
