package com.spring.toogoodtogo.serviceTest;

import com.spring.toogoodtogo.store.dto.CreateStoreRequest;
import com.spring.toogoodtogo.store.repository.StoreRepository;
import com.spring.toogoodtogo.store.service.StoreService;
import com.spring.toogoodtogo.user.domain.User;
import com.spring.toogoodtogo.user.repository.UserRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.util.Optional;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class StoreServiceTest {

    @Mock
    StoreRepository storeRepository;
    @Mock
    UserRepository userRepository;

    @InjectMocks
    StoreService storeService;

    private CreateStoreRequest request(String name, String address){
        var r = new CreateStoreRequest();
        r.setName(name);
        r.setAddress(address);
        r.setPhone("010-0000-0000");
        r.setDescription("desc");
        r.setLatitude(new BigDecimal("37.500000"));
        r.setLongitude(new BigDecimal("127.000000"));
        return r;
    }

    @Test
    @DisplayName("사장님 권한/활성화가 아니면 매장 생성 실패")
    void createStore(){
        User owner = mock(User.class);
        when(userRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(owner.isStoreOwner()).thenReturn(true);
        when(owner.isEnabled()).thenReturn(Boolean.TRUE);

        Assertions.assertThatThrownBy(() -> storeService.create(1L,request("척척박사","인천시 서구"))).isInstanceOf(IllegalArgumentException.class);
    }

}
