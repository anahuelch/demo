package com.example.demo.controler;

import com.example.demo.model.Message;
import com.example.demo.model.NewUser;
import com.example.demo.model.User;
import com.example.demo.service.IPhoneService;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;
    private final IPhoneService phoneService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        try{
            User newUser = userService.createUser(user);

            NewUser outputUser = new NewUser();
            outputUser.id = newUser.getId().toString();
            outputUser.created = newUser.getCreationTime().toString();
            outputUser.modified = "";
            outputUser.last_login = newUser.getLastLogin().toString();
            outputUser.isactive = newUser.isActive();
            outputUser.token = "";

            return new ResponseEntity(outputUser, HttpStatus.CREATED);
        }
        catch(Exception ex){
            if(ex.getCause().toString().contains("ConstraintViolationException: could not execute statement")){
                return new ResponseEntity(new Message("El correo ya ha sido registrado"), HttpStatus.FORBIDDEN);
            }
            else{
                return new ResponseEntity(new Message(ex.getMessage()), HttpStatus.FORBIDDEN);
            }
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity getUser(@PathVariable("id") Long id){
        try {
            User user = userService.getUser(id);
            return new ResponseEntity(user, HttpStatus.OK);
        }
        catch(Exception ex){
            return new ResponseEntity(new Message(ex.getMessage()), HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity deleteUser(@PathVariable("id") Long id){
        if(userService.deleteUser(id)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
