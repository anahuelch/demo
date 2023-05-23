package com.example.demo.controler;

import com.example.demo.model.Message;
import com.example.demo.model.OutputUser;
import com.example.demo.model.User;
import com.example.demo.service.IUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/users")
@RequiredArgsConstructor
public class UserController {

    private final IUserService userService;

    @PostMapping
    public ResponseEntity createUser(@RequestBody User user){
        try{
            User newUser = userService.createUser(user);

            //Usamos clase auxiliar para devolver los datos del usuario exactamente como se pide en el enunciado.
            OutputUser outputUser = new OutputUser(newUser.getId().toString(), newUser.getCreationTime().toString(), null, newUser.getLastLogin().toString(), newUser.isActive(), "");

            return new ResponseEntity(outputUser, HttpStatus.CREATED);
        }
        catch(Exception ex){
            return new ResponseEntity(new Message(ex.getMessage()), HttpStatus.FORBIDDEN);
        }
    }

    @GetMapping
    public ResponseEntity getAllUsers(){
        List<User> users = userService.getAllUsers();
        return new ResponseEntity(users, HttpStatus.OK);
    }

    @DeleteMapping("/{email}")
    public ResponseEntity deleteUser(@PathVariable("email") String email){

        User user = userService.getUser(email);
        if(userService.deleteUser(user)){
            return new ResponseEntity(HttpStatus.OK);
        }
        else{
            return new ResponseEntity(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
