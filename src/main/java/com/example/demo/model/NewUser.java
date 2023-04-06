package com.example.demo.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class NewUser {
    public String id;
    public String created;
    public String modified;
    public String last_login;
    public Boolean isactive;
    public String token;
}
