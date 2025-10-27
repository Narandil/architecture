package com.wgt.imt.architecture.infrastructures.events.comptes;

import com.wgt.imt.architecture.business.common.model.TypeCompteEnum;
import com.wgt.imt.architecture.business.comptes.model.Compte;
import com.wgt.imt.architecture.infrastructures.events.comptes.model.MouvementCompteEvent;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.context.ApplicationEventPublisher;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@DisplayName("MouvementCompteEventPublisher")
class MouvementCompteEventPublisherTest {

    @Mock
    private ApplicationEventPublisher eventPublisher;

    @InjectMocks
    private MouvementCompteEventPublisher mouvementCompteEventPublisher;

    @Captor
    private ArgumentCaptor<MouvementCompteEvent> eventCaptor;

    private UUID clientUUID;
    private UUID compteUUID;

    @BeforeEach
    void setUp() {
        clientUUID = UUID.randomUUID();
        compteUUID = UUID.randomUUID();
    }

    @Nested
    @DisplayName("accept(UUID, Compte)")
    class Accept {

        @Test
        @DisplayName("Devrait publier un événement quand le solde est négatif")
        void shouldPublishEventWhenBalanceIsNegative() {
            // Given
            Compte compteNegatif = Compte.builder()
                    .identifier(compteUUID)
                    .name("Compte_Test")
                    .type(TypeCompteEnum.COMPTE_COURANT)
                    .solde(-50.0)
                    .build();

            // When
            mouvementCompteEventPublisher.accept(clientUUID, compteNegatif);

            // Then
            verify(eventPublisher, times(1)).publishEvent(eventCaptor.capture());
            MouvementCompteEvent capturedEvent = eventCaptor.getValue();
            assertNotNull(capturedEvent);
            assertEquals(clientUUID, capturedEvent.getClientIdentifier());
            assertEquals(compteUUID, capturedEvent.getCompteIdentifier());
        }

        @Test
        @DisplayName("Devrait publier un événement quand le solde est exactement -0.01")
        void shouldPublishEventWhenBalanceIsSlightlyNegative() {
            // Given
            Compte compte = Compte.builder()
                    .identifier(compteUUID)
                    .name("Compte_Test")
                    .type(TypeCompteEnum.COMPTE_COURANT)
                    .solde(-0.01)
                    .build();

            // When
            mouvementCompteEventPublisher.accept(clientUUID, compte);

            // Then
            verify(eventPublisher, times(1)).publishEvent(any(MouvementCompteEvent.class));
        }

        @Test
        @DisplayName("Devrait lever NullPointerException si clientIdentifier est null")
        void shouldThrowNullPointerExceptionWhenClientIdentifierIsNull() {
            // Given
            Compte compte = Compte.builder()
                    .identifier(compteUUID)
                    .name("Compte_Test")
                    .type(TypeCompteEnum.COMPTE_COURANT)
                    .solde(-50.0)
                    .build();

            // When & Then
            assertThrows(NullPointerException.class,
                () -> mouvementCompteEventPublisher.accept(null, compte));

            verify(eventPublisher, never()).publishEvent(any());
        }

        @Test
        @DisplayName("Devrait lever NullPointerException si compte est null")
        void shouldThrowNullPointerExceptionWhenCompteIsNull() {
            // When & Then
            assertThrows(NullPointerException.class,
                () -> mouvementCompteEventPublisher.accept(clientUUID, null));

            verify(eventPublisher, never()).publishEvent(any());
        }
    }
}

