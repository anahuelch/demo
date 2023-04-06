package com.example.demo.service;

import com.example.demo.model.Phone;
import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.regex.Pattern;

@Service
@AllArgsConstructor
public class UserService implements IUserService{

    private final IUserRepository userRepository;
    private final IPhoneService phoneService;

    @Override
    public User createUser(User user) throws Exception {
        //Validamos el formato del email y del password
        if(!isAValidEmail(user.getEmail())) {
            throw new Exception("El email no cumple con el formato", new Throwable());
        } else if (!isAValidPassword(user.getPassword())) {
            throw new Exception("La contraseña no cumple con el formato", new Throwable());
        }

        user.setActive(true);
        User newUser = userRepository.save(user);

        List<Phone> phones = user.getPhones();
        for(int i=0; i < phones.size(); i++){
            phones.get(i).setUser(newUser);
        }
        phoneService.createPhones(phones);

        return newUser;
    }

    @Override
    public User getUser(Long id) {
        return userRepository.findById(id).get();
    }

    @Override
    public boolean deleteUser(Long id) {
        try{
            userRepository.deleteById(id);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    public boolean existById(Long id){
        return userRepository.existsById(id);
    }

    public boolean isAValidEmail(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    public boolean isAValidPassword(String password) {
        String regexPattern = "^(?=.*[A-Z])(?=.*[0-9].*[0-9]).+$";
        return Pattern.compile(regexPattern)
                .matcher(password)
                .matches();
    }
}
