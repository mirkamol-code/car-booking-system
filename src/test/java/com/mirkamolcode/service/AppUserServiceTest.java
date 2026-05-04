package com.mirkamolcode.service;

import com.mirkamolcode.exception.ResourceNotFoundException;
import com.mirkamolcode.model.AppUser;
import com.mirkamolcode.repository.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static com.mirkamolcode.model.enums.ResponseMessage.UNKNOWN_USER;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.*;


@ExtendWith(MockitoExtension.class)
class AppUserServiceTest {

    @Mock
    private AppUserRepository userRepository;

    @InjectMocks
    private AppUserService underTest;

    @Test
    void shouldGetAllAppUsers() {
        // given
        List<AppUser> expected = List.of(
                new AppUser("Jamila"),
                new AppUser("James"));
        given(userRepository.findAll()).willReturn(expected);
        // when
        List<AppUser> actual = underTest.getAllUsers();
        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldReturnEmptyListWhenUserListIsEmpty() {
        // given
        given(userRepository.findAll()).willReturn(new ArrayList<>());

        // then
        assertThat(underTest.getAllUsers()).isEmpty();
    }

    @Test
    void shouldGetUserById(){
        // given
        AppUser expected = new AppUser(UUID.randomUUID(), "Jamila");
        given(userRepository.findById(any())).willReturn(Optional.of(expected));

        // when
        AppUser actual = underTest.getUserById(expected.getId());

        // then
        assertThat(actual).isEqualTo(expected);
    }

    @Test
    void shouldThrowWhenUserIsNotFound(){
        // given
        UUID id = UUID.randomUUID();
        given(userRepository.findById(id)).willReturn(Optional.empty());

        // when
        assertThatThrownBy(() -> underTest.getUserById(id))
                .hasMessageContaining(UNKNOWN_USER.getMessage())
                .isInstanceOf(ResourceNotFoundException.class);
    }


}