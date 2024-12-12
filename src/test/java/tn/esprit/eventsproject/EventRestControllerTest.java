package tn.esprit.eventsproject;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import tn.esprit.eventsproject.controllers.EventRestController;
import tn.esprit.eventsproject.entities.Event;
import tn.esprit.eventsproject.entities.Logistics;
import tn.esprit.eventsproject.entities.Participant;
import tn.esprit.eventsproject.services.IEventServices;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class EventRestControllerTest {

    @Mock
    private IEventServices eventServices;

    @InjectMocks
    private EventRestController eventRestController;

    private Event event;
    private Participant participant;
    private Logistics logistics;

    @BeforeEach
    void setUp() {
        // Initialize test data
        event = new Event();
        event.setIdEvent(1);
        event.setDescription("Test Event");

        participant = new Participant();
        participant.setIdPart(1);
        participant.setNom("Test Participant");

        logistics = new Logistics();
        logistics.setIdLog(1);
        logistics.setDescription("Test Logistics");
    }

    @Test
    void testAddParticipant() {
        // Arrange
        when(eventServices.addParticipant(participant)).thenReturn(participant);

        // Act
        Participant result = eventRestController.addParticipant(participant);

        // Assert
        assertNotNull(result);
        assertEquals("Test Participant", result.getNom());
        verify(eventServices, times(1)).addParticipant(participant);
    }

    @Test
    void testAddEventWithParticipant() {
        // Arrange
        when(eventServices.addAffectEvenParticipant(event, 1)).thenReturn(event);

        // Act
        Event result = eventRestController.addEventPart(event, 1);

        // Assert
        assertNotNull(result);
        assertEquals("Test Event", result.getDescription());
        verify(eventServices, times(1)).addAffectEvenParticipant(event, 1);
    }

    @Test
    void testAddEvent() {
        // Arrange
        when(eventServices.addAffectEvenParticipant(event)).thenReturn(event);

        // Act
        Event result = eventRestController.addEvent(event);

        // Assert
        assertNotNull(result);
        assertEquals("Test Event", result.getDescription());
        verify(eventServices, times(1)).addAffectEvenParticipant(event);
    }

    @Test
    void testAddAffectLog() {
        // Arrange
        String description = "Test Event";
        when(eventServices.addAffectLog(logistics, description)).thenReturn(logistics);

        // Act
        Logistics result = eventRestController.addAffectLog(logistics, description);

        // Assert
        assertNotNull(result);
        assertEquals("Test Logistics", result.getDescription());
        verify(eventServices, times(1)).addAffectLog(logistics, description);
    }

    @Test
    void testGetLogistiquesDates() {
        // Arrange
        LocalDate date1 = LocalDate.of(2024, 1, 1);
        LocalDate date2 = LocalDate.of(2024, 12, 31);
        List<Logistics> logisticsList = new ArrayList<>();
        logisticsList.add(logistics);

        when(eventServices.getLogisticsDates(date1, date2)).thenReturn(logisticsList);

        // Act
        List<Logistics> result = eventRestController.getLogistiquesDates(date1, date2);

        // Assert
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test Logistics", result.get(0).getDescription());
        verify(eventServices, times(1)).getLogisticsDates(date1, date2);
    }
}
