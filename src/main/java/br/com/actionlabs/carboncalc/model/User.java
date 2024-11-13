package br.com.actionlabs.carboncalc.model;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class User {
    private String name;
    private String email;
    private String uf;
    private String phoneNumber;
}
