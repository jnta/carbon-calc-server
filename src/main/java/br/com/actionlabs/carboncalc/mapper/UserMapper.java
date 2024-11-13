package br.com.actionlabs.carboncalc.mapper;

import br.com.actionlabs.carboncalc.dto.StartCalcRequestDTO;
import br.com.actionlabs.carboncalc.model.User;

public class UserMapper {
    private UserMapper() {
    }

    public static User mapToUser(StartCalcRequestDTO startCalcRequestDTO) {
        return User.builder()
                .name(startCalcRequestDTO.getName())
                .email(startCalcRequestDTO.getEmail())
                .uf(startCalcRequestDTO.getUf())
                .phoneNumber(startCalcRequestDTO.getPhoneNumber())
                .build();
    }
}
