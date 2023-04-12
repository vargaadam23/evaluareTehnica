package com.adam.evaluaretehnica.quest;

import com.adam.evaluaretehnica.badge.BadgeService;
import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.exception.QuestCreationException;
import com.adam.evaluaretehnica.quest.http.QuestCreationRequest;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.util.ApplicationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.TaskScheduler;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.TemporalAmount;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

//TODO refactor raw values to variables
@ExtendWith(MockitoExtension.class)
class QuestServiceTest {
    @Mock
    private QuestRepository questRepository;
    @Mock
    private BadgeService badgeService;
    @Mock
    private UserService userService;
    @Mock
    private TaskScheduler taskScheduler;
    @Mock
    private ApplicationProperties applicationProperties;
    @InjectMocks
    private QuestService questService;

    private QuestCreationRequest getMockRequest(String date, List<Long> users){
        return new QuestCreationRequest(
                "name",
                "description",
                "short description",
                date,
                users,
                false,
                200
        );
    }

    private List<User> getUserList(){
        User currentUser = new User();
        User user2 = new User();
        User user3 = new User();
        currentUser.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
        user2.setUserQuests(new ArrayList<>());
        user3.setUserQuests(new ArrayList<>());
        currentUser.setUserQuests(new ArrayList<>());

        return List.of(currentUser,user2,user3);
    }

    private User getCurrentUser(){
        User currentUser = new User();
        currentUser.setId(4L);
        currentUser.setCurrencyTokens(200);
        return currentUser;
    }

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void createQuestWithCreationRequestWithCreationExceptionExpireDate() throws NotEnoughTokensException, QuestCreationException {
        QuestCreationRequest creationRequest = getMockRequest(LocalDateTime.now().toString(), List.of(1L,2L,3L));
        when(applicationProperties.getConfigValue("maxExpirationDuration")).thenReturn("2");
        when(userService.getCurrentUser()).thenReturn(getCurrentUser());

        Exception e = null;
        try {
            questService.createQuestWithCreationRequest(creationRequest);
        }catch (
                Exception ex
        ){
            e = ex;
        }

        assert e != null;
        assertEquals(e.getMessage(), "Quest expiration needs to be further than 2 minutes!");
    }

    @Test
    void createQuestWithCreationRequestWithCreationExceptionCurrentUserAdded() throws NotEnoughTokensException, QuestCreationException {
        QuestCreationRequest creationRequest = getMockRequest(LocalDateTime.now().toString(), List.of(1L,2L,3L, 4L));
        when(userService.getCurrentUser()).thenReturn(getCurrentUser());

        Exception e = null;
        try {
            questService.createQuestWithCreationRequest(creationRequest);
        }catch (
                Exception ex
        ){
            e = ex;
        }

        assert e != null;
        assertEquals(e.getMessage(), "Quest master can not be assigned to quest as user!");
    }

    @Test
    void createQuestWithCreationRequestWithCreationExceptionUsersNotFound() throws NotEnoughTokensException, QuestCreationException {
        QuestCreationRequest creationRequest = getMockRequest(LocalDateTime.now().plusMinutes(5).toString(), List.of());
        when(userService.getCurrentUser()).thenReturn(getCurrentUser());
        when(applicationProperties.getConfigValue("maxExpirationDuration")).thenReturn("2");
        when(userService.getUsersBasedOnIdList(any(List.class))).thenReturn(List.of());

        Exception e = null;
        try {
            questService.createQuestWithCreationRequest(creationRequest);
        }catch (
                Exception ex
        ){
            e = ex;
        }

        assert e != null;
        assertEquals(e.getMessage(), "Provided users were not found while creating quest!");
    }

    @Test
    void createQuestWithCreationRequestWithCreation() throws NotEnoughTokensException, QuestCreationException {
        QuestCreationRequest creationRequest = getMockRequest(LocalDateTime.now().plusMinutes(5).toString(), List.of(1L,2L,3L));
        when(userService.getCurrentUser()).thenReturn(getCurrentUser());
        when(applicationProperties.getConfigValue("maxExpirationDuration")).thenReturn("2");
        when(userService.getUsersBasedOnIdList(any(List.class))).thenReturn(getUserList());

        Quest quest= questService.createQuestWithCreationRequest(creationRequest);

        assertEquals(quest.getQuestMaster().getCurrencyTokens(), 0);
        assertEquals(quest.getQuestMaster().getId(), 4L);

        assertFalse(quest.getAssignedUserQuests().isEmpty());
        assertEquals(quest.getIndividualTokenPrize(), 200 / 3);
        assertTrue(quest.getAssignedUserQuests().stream().anyMatch(userQuest -> userQuest.getUser().getId().equals(1L)));
    }
}