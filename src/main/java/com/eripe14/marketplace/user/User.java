package com.eripe14.marketplace.user;

import eu.okaeri.configs.annotation.CustomKey;
import eu.okaeri.persistence.document.Document;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.UUID;

@Data
@EqualsAndHashCode(callSuper = false)
public class User extends Document {

    @CustomKey("user-name")
    private String name;

    public UUID getUniqueId() {
        return this.getPath().toUUID();
    }

}