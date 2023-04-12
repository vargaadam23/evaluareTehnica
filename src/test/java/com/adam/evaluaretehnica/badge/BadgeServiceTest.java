package com.adam.evaluaretehnica.badge;

import com.adam.evaluaretehnica.badge.Badge;
import com.adam.evaluaretehnica.badge.BadgeRepository;
import com.adam.evaluaretehnica.badge.BadgeService;
import com.adam.evaluaretehnica.badge.http.BadgeCreationRequest;
import com.adam.evaluaretehnica.badge.types.BadgeType;
import com.adam.evaluaretehnica.badge.types.RankBadge;
import com.adam.evaluaretehnica.exception.NotEnoughTokensException;
import com.adam.evaluaretehnica.user.User;
import com.adam.evaluaretehnica.user.UserRepository;
import com.adam.evaluaretehnica.user.UserService;
import com.adam.evaluaretehnica.util.ApplicationProperties;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

//TODO refactor
@ExtendWith(MockitoExtension.class)
class BadgeServiceTest {

    @Mock
    private BadgeRepository badgeRepository;
    @Mock
    private UserRepository userRepository;
    @Mock
    private UserService userService;
    @Mock
    private ApplicationProperties applicationProperties;
    @InjectMocks
    private BadgeService badgeService;

    private List<Badge> badgeList;

    private Badge createMockBadge(int id){
        Badge badge1 = id % 2 == 0 ? BadgeType.RANK.createBadge() : BadgeType.CURRENCY_TOKEN.createBadge();
        HashMap<String,String> cssStyles = new HashMap<>();
        cssStyles.put("margin"+id,"1px");
        cssStyles.put("padding"+id,"2px");
        badge1.setTitle("title"+id);
        badge1.setConditionDescription("desc"+id);
        badge1.setId((long) id);
        badge1.setCssStyles(cssStyles);

        return badge1;
    }

    @BeforeEach
    void setUp() {
        badgeList = List.of(
                createMockBadge(1),
                createMockBadge(2),
                createMockBadge(3),
                createMockBadge(4)
        );



    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void shouldGetAllBadges() {
        //Arrange
        when(badgeRepository.findAll()).thenReturn(badgeList);

        //Act
        List<Badge> badges = badgeService.getAllBadges();

        //Assert
        assertThat(badges).usingRecursiveComparison().isEqualTo(badgeList);
        verify(badgeRepository, times(1)).findAll();
        verifyNoMoreInteractions(badgeRepository);
    }

    @Test
    public void shouldCreateBadge() throws NotEnoughTokensException {
        //Arrange
        HashMap<String,String> cssStyles = new HashMap<>();
        cssStyles.put("margin","1px");
        cssStyles.put("padding","2px");
        BadgeCreationRequest badgeCreationRequest = new BadgeCreationRequest(
                "title",1,"description",BadgeType.RANK,cssStyles,"1"
        );

        Badge badgeNew = BadgeType.RANK.createBadge();
        badgeNew.setValue(badgeCreationRequest.conditionValue());
        badgeNew.setImportance(1);
        badgeNew.setTitle("title");
        badgeNew.setConditionDescription(("description"));
        badgeNew.setCssStyles(cssStyles);

        User currentUser = new User();
        User user2 = new User();
        User user3 = new User();
        currentUser.setCurrencyTokens(100);
        currentUser.setBadges(new ArrayList<>());
        user2.setBadges(new ArrayList<>());
        user3.setBadges(new ArrayList<>());
        user2.setCurrencyTokens(200);
        user2.setRank(1);
        currentUser.setRank(2);
        user3.setRank(3);
        user3.setCurrencyTokens(50);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(applicationProperties.getConfigValue("badgeCreationCost")).thenReturn("50");

        //Act
        Badge newBadge = badgeService.createBadge(badgeCreationRequest);

        //Assert
        verify(userService, times(1)).getCurrentUser();
        verify(applicationProperties, times(1)).getConfigValue("badgeCreationCost");
        verify(badgeRepository, times(1)).save(any(Badge.class));
        verify(userRepository, times(1)).saveAll(anyList());
        assertEquals(newBadge.getTitle(), "title");
        assertEquals(badgeNew.getConditionDescription(), "description");
        assertEquals(badgeNew.getBadgeType(), BadgeType.RANK);
        assertEquals(currentUser.getCurrencyTokens(), 50);
    }

    @Test
    public void shouldCreateBadgeWithNotEnoughTokens() throws NotEnoughTokensException {
        //Arrange
        HashMap<String,String> cssStyles = new HashMap<>();
        cssStyles.put("margin","1px");
        cssStyles.put("padding","2px");
        BadgeCreationRequest badgeCreationRequest = new BadgeCreationRequest(
                "title",1,"description",BadgeType.RANK,cssStyles,"1"
        );

        Badge badgeNew = BadgeType.RANK.createBadge();
        badgeNew.setValue(badgeCreationRequest.conditionValue());
        badgeNew.setImportance(1);
        badgeNew.setTitle("title");
        badgeNew.setConditionDescription(("description"));
        badgeNew.setCssStyles(cssStyles);

        User currentUser = new User();
        User user2 = new User();
        User user3 = new User();
        currentUser.setCurrencyTokens(20);
        currentUser.setBadges(new ArrayList<>());
        user2.setBadges(new ArrayList<>());
        user3.setBadges(new ArrayList<>());
        user2.setCurrencyTokens(200);
        user2.setRank(1);
        currentUser.setRank(2);
        user3.setRank(3);
        user3.setCurrencyTokens(50);

        when(userService.getCurrentUser()).thenReturn(currentUser);
        when(applicationProperties.getConfigValue("badgeCreationCost")).thenReturn("50");

        Badge newBadge = null;
        boolean errorThrown = false;

        //Act
        try {
            newBadge = badgeService.createBadge(badgeCreationRequest);
        }catch (NotEnoughTokensException e){
            errorThrown = true;
        }


        //Assert
        verify(userService, times(1)).getCurrentUser();
        verify(applicationProperties, times(1)).getConfigValue("badgeCreationCost");
        verify(badgeRepository, times(0)).save(any(Badge.class));
        verify(userRepository, times(0)).saveAll(anyList());
        assertTrue(errorThrown);
    }

    @Test
    public void shouldAddRankBadgeForEligibleUsers(){
        User currentUser = new User();
        User user2 = new User();
        User user3 = new User();
        currentUser.setCurrencyTokens(100);
        currentUser.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
        currentUser.setBadges(new ArrayList<>());
        user2.setBadges(new ArrayList<>());
        user3.setBadges(new ArrayList<>());

        user2.setRank(1);
        currentUser.setRank(2);
        user3.setRank(3);
        user3.setCurrencyTokens(50);

        List<User> userList = List.of(currentUser, user2, user3);

        Badge badgeNew = BadgeType.RANK.createBadge();
        badgeNew.setValue("2");

        when(userRepository.findAll()).thenReturn(userList);

        badgeService.addBadgeToEligibleUsers(badgeNew);

        assertThat(currentUser.getBadges().isEmpty()).isFalse();
        assertThat(user2.getBadges().isEmpty()).isFalse();
        assertThat(user3.getBadges().isEmpty()).isTrue();
    }

    @Test
    public void shouldAddCurrencyBadgeForEligibleUsers(){
        User currentUser = new User();
        User user2 = new User();
        User user3 = new User();
        currentUser.setCurrencyTokens(200);
        currentUser.setId(1L);
        user2.setId(2L);
        user3.setId(3L);
        currentUser.setBadges(new ArrayList<>());
        user2.setBadges(new ArrayList<>());
        user3.setBadges(new ArrayList<>());
        user2.setCurrencyTokens(300);
        user2.setRank(1);
        currentUser.setRank(2);
        user3.setRank(3);
        user3.setCurrencyTokens(50);

        List<User> userList = List.of(currentUser, user2, user3);

        Badge badgeNew = BadgeType.CURRENCY_TOKEN.createBadge();
        badgeNew.setValue("200");

        when(userRepository.findAll()).thenReturn(userList);

        badgeService.addBadgeToEligibleUsers(badgeNew);

        assertThat(currentUser.getBadges().isEmpty()).isFalse();
        assertThat(user2.getBadges().isEmpty()).isFalse();
        assertThat(user3.getBadges().isEmpty()).isTrue();
    }

    @Test
    void shouldVerifyAndAddBadgesToUser() {
        Badge badge1 = createMockBadge(1);
        badge1.setValue("200");
        Badge badge2 = createMockBadge(2);
        badge2.setValue("2");
        Badge badge3 = createMockBadge(3);
        badge3.setValue("100");
        Badge badge4 = createMockBadge(4);
        badge4.setValue("1");
        Badge badge5 = createMockBadge(5);
        badge5.setValue("400");

        User user = new User();
        user.setBadges(new ArrayList<>());
        user.addBadge(badge3);
        user.setCurrencyTokens(200);
        user.setRank(2);

        List<Badge> badgeList = new ArrayList<>(
                List.of(
                        badge1,
                badge2,
                badge3,
                badge4,
                badge5)
        );

        when(badgeRepository.findAll()).thenReturn(badgeList);

        badgeService.verifyAndAddBadgesToUser(user);

        assertThat(user.getBadges().size()).isEqualTo(3);
        assertThat(user.getBadges().contains(badge2)).isTrue();
        assertThat(user.getBadges().contains(badge3)).isTrue();
        assertThat(user.getBadges().contains(badge1)).isTrue();
    }
}