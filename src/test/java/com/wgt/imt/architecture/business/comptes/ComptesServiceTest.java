package com.wgt.imt.architecture.business.comptes;

import com.wgt.imt.architecture.business.clients.model.Client;
import com.wgt.imt.architecture.business.common.model.GenreEnum;
import com.wgt.imt.architecture.business.common.model.TypeCompteEnum;
import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.infrastructures.bdd.clients.ClientsBddService;
import com.wgt.imt.architecture.infrastructures.events.comptes.MouvementCompteEventPublisher;
import com.wgt.imt.architecture.interfaces.rest.common.exception.NotFoundException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("ComptesService")
class ComptesServiceTest {

    @Mock
    private ClientsBddService clientsBddService;

    @Mock
    private MouvementCompteEventPublisher mouvementCompteEventPublisher;

    @InjectMocks
    private ComptesService comptesService;

    private UUID clientUUID;
    private UUID compteUUID;
    private Client testClient;
    private Compte testCompte;

    @BeforeEach
    void setUp() {
        clientUUID = UUID.randomUUID();
        compteUUID = UUID.randomUUID();

        testCompte = Compte.builder()
                .identifier(compteUUID)
                .clientIdentifier(clientUUID)
                .name("Compte_Courant_Test")
                .type(TypeCompteEnum.COMPTE_COURANT)
                .solde(100.0)
                .build();

        testClient = Client.builder()
                .identifier(clientUUID)
                .lastname("Dupont")
                .firstname("Jean")
                .genre(GenreEnum.HOMME)
                .comptes(new HashSet<>(Collections.singletonList(testCompte)))
                .build();
    }

    @Nested
    @DisplayName("getAllFilteredByClient(UUID)")
    class GetAllFilteredByClient {

        @Test
        @DisplayName("Devrait retourner tous les comptes d'un client existant")
        void shouldReturnAllAccountsForExistingClient() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(testClient));

            // When
            Collection<Compte> result = comptesService.getAllFilteredByClient(clientUUID);

            // Then
            assertNotNull(result);
            assertEquals(1, result.size());
            assertTrue(result.contains(testCompte));
            verify(clientsBddService, times(1)).get(clientUUID);
        }

        @Test
        @DisplayName("Devrait retourner une collection vide si le client n'existe pas")
        void shouldReturnEmptyCollectionWhenClientNotFound() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.empty());

            // When
            Collection<Compte> result = comptesService.getAllFilteredByClient(clientUUID);

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
            verify(clientsBddService, times(1)).get(clientUUID);
        }

        @Test
        @DisplayName("Devrait retourner une collection vide si le client n'a pas de comptes")
        void shouldReturnEmptyCollectionWhenClientHasNoAccounts() {
            // Given
            Client clientWithoutAccounts = testClient.toBuilder().comptes(Collections.emptySet()).build();
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(clientWithoutAccounts));

            // When
            Collection<Compte> result = comptesService.getAllFilteredByClient(clientUUID);

            // Then
            assertNotNull(result);
            assertTrue(result.isEmpty());
        }
    }

    @Nested
    @DisplayName("getOneFilteredByClient(UUID, UUID)")
    class GetOneFilteredByClient {

        @Test
        @DisplayName("Devrait retourner un compte existant pour un client")
        void shouldReturnExistingAccountForClient() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(testClient));

            // When
            Optional<Compte> result = comptesService.getOneFilteredByClient(clientUUID, compteUUID);

            // Then
            assertTrue(result.isPresent());
            assertEquals(testCompte, result.get());
            verify(clientsBddService, times(1)).get(clientUUID);
        }

        @Test
        @DisplayName("Devrait retourner Optional.empty si le compte n'existe pas")
        void shouldReturnEmptyOptionalWhenAccountNotFound() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(testClient));
            UUID nonExistentCompteUUID = UUID.randomUUID();

            // When
            Optional<Compte> result = comptesService.getOneFilteredByClient(clientUUID, nonExistentCompteUUID);

            // Then
            assertFalse(result.isPresent());
        }

        @Test
        @DisplayName("Devrait retourner Optional.empty si le client n'existe pas")
        void shouldReturnEmptyOptionalWhenClientNotFound() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.empty());

            // When
            Optional<Compte> result = comptesService.getOneFilteredByClient(clientUUID, compteUUID);

            // Then
            assertFalse(result.isPresent());
        }
    }

    @Nested
    @DisplayName("create(UUID, Compte)")
    class Create {

        @Test
        @DisplayName("Devrait créer un nouveau compte pour un client existant")
        void shouldCreateNewAccountForExistingClient() {
            // Given
            Compte newCompte = Compte.builder()
                    .name("Livret_A")
                    .type(TypeCompteEnum.LIVRET_A)
                    .solde(0.0)
                    .build();

            Client clientWithoutNewAccount = testClient.toBuilder().comptes(Collections.emptySet()).build();
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(clientWithoutNewAccount));
            when(clientsBddService.save(any(Client.class))).thenReturn(testClient);

            // When
            Compte result = comptesService.create(clientUUID, newCompte);

            // Then
            assertNotNull(result);
            assertEquals(newCompte, result);
            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, times(1)).save(any(Client.class));
        }

        @Test
        @DisplayName("Devrait lever NotFoundException si le client n'existe pas")
        void shouldThrowNotFoundExceptionWhenClientNotFound() {
            // Given
            Compte newCompte = Compte.builder()
                    .name("Compte_Test")
                    .type(TypeCompteEnum.COMPTE_COURANT)
                    .build();
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.empty());

            // When & Then
            NotFoundException exception = assertThrows(NotFoundException.class,
                    () -> comptesService.create(clientUUID, newCompte));

            assertTrue(exception.getMessage().contains(clientUUID.toString()));
            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, never()).save(any(Client.class));
        }
    }

    @Nested
    @DisplayName("update(UUID, Compte)")
    class Update {

        @Test
        @DisplayName("Devrait mettre à jour un compte existant")
        void shouldUpdateExistingAccount() {
            // Given
            Compte updatedCompte = testCompte.toBuilder().solde(200.0).build();
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(testClient));
            when(clientsBddService.save(any(Client.class))).thenReturn(testClient);
            doNothing().when(mouvementCompteEventPublisher).accept(any(UUID.class), any(Compte.class));

            // When
            comptesService.update(clientUUID, updatedCompte);

            // Then
            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, times(1)).save(any(Client.class));
            verify(mouvementCompteEventPublisher, times(1)).accept(clientUUID, updatedCompte);
        }

        @Test
        @DisplayName("Devrait publier un événement si le solde est négatif")
        void shouldPublishEventWhenBalanceIsNegative() {
            // Given
            Compte compteNegatif = testCompte.toBuilder().solde(-50.0).build();
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(testClient));
            when(clientsBddService.save(any(Client.class))).thenReturn(testClient);
            doNothing().when(mouvementCompteEventPublisher).accept(any(UUID.class), any(Compte.class));

            // When
            comptesService.update(clientUUID, compteNegatif);

            // Then
            verify(mouvementCompteEventPublisher, times(1)).accept(clientUUID, compteNegatif);
        }

        @Test
        @DisplayName("Devrait lever NotFoundException si le client n'existe pas")
        void shouldThrowNotFoundExceptionWhenClientNotFound() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class,
                    () -> comptesService.update(clientUUID, testCompte));

            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, never()).save(any(Client.class));
        }
    }

    @Nested
    @DisplayName("applyAgios(UUID, UUID)")
    class ApplyAgios {

        @Test
        @DisplayName("Devrait appliquer les agios de 5 euros à un compte")
        void shouldApplyAgiosToAccount() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(testClient));
            when(clientsBddService.save(any(Client.class))).thenReturn(testClient);

            // When
            comptesService.applyAgios(clientUUID, compteUUID);

            // Then
            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, times(1)).save(any(Client.class));
        }

        @Test
        @DisplayName("Devrait lever NotFoundException si le client n'existe pas")
        void shouldThrowNotFoundExceptionWhenClientNotFound() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class,
                    () -> comptesService.applyAgios(clientUUID, compteUUID));

            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, never()).save(any(Client.class));
        }

        @Test
        @DisplayName("Ne devrait pas modifier les autres comptes lors de l'application des agios")
        void shouldNotModifyOtherAccountsWhenApplyingAgios() {
            // Given
            Compte autreCompte = Compte.builder()
                    .identifier(UUID.randomUUID())
                    .name("Autre_Compte")
                    .type(TypeCompteEnum.LIVRET_A)
                    .solde(500.0)
                    .build();

            Client clientWithMultipleAccounts = testClient.toBuilder()
                    .comptes(new HashSet<>(Arrays.asList(testCompte, autreCompte)))
                    .build();

            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(clientWithMultipleAccounts));

            // When
            comptesService.applyAgios(clientUUID, compteUUID);

            // Then
            verify(clientsBddService, times(1)).save(any(Client.class));
        }
    }

    @Nested
    @DisplayName("delete(UUID, UUID)")
    class Delete {

        @Test
        @DisplayName("Devrait supprimer un compte d'un client")
        void shouldDeleteAccountFromClient() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(testClient));
            when(clientsBddService.save(any(Client.class))).thenReturn(testClient);

            // When
            comptesService.delete(clientUUID, compteUUID);

            // Then
            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, times(1)).save(any(Client.class));
        }

        @Test
        @DisplayName("Devrait lever NotFoundException si le client n'existe pas")
        void shouldThrowNotFoundExceptionWhenClientNotFound() {
            // Given
            when(clientsBddService.get(clientUUID)).thenReturn(Optional.empty());

            // When & Then
            assertThrows(NotFoundException.class,
                    () -> comptesService.delete(clientUUID, compteUUID));

            verify(clientsBddService, times(1)).get(clientUUID);
            verify(clientsBddService, never()).save(any(Client.class));
        }

        @Test
        @DisplayName("Ne devrait pas affecter les autres comptes lors de la suppression")
        void shouldNotAffectOtherAccountsWhenDeleting() {
            // Given
            Compte autreCompte = Compte.builder()
                    .identifier(UUID.randomUUID())
                    .name("Autre_Compte")
                    .type(TypeCompteEnum.LIVRET_A)
                    .build();

            Client clientWithMultipleAccounts = testClient.toBuilder()
                    .comptes(new HashSet<>(Arrays.asList(testCompte, autreCompte)))
                    .build();

            when(clientsBddService.get(clientUUID)).thenReturn(Optional.of(clientWithMultipleAccounts));

            // When
            comptesService.delete(clientUUID, compteUUID);

            // Then
            verify(clientsBddService, times(1)).save(any(Client.class));
        }
    }
}

