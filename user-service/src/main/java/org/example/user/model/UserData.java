package org.example.user.model;

import lombok.Data;
import lombok.experimental.Accessors;

import java.util.UUID;

@Data
@Accessors(chain = true)
public class UserData {
    private UUID id;
    private String email;
    private String additionalInfo;
}
