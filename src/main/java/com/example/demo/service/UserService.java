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
        //Hacemos algunas validaciones requeridas:
        if(!isAValidEmail(user.getEmail())) {
            throw new Exception("El email no cumple con el formato", new Throwable());
        } else if (!isAValidPassword(user.getPassword())) {
            throw new Exception("La contraseña no cumple con el formato", new Throwable());
        } else if (userRepository.findByEmail(user.getEmail()) != null) {
            throw new Exception("El correo ya ha sido registrado", new Throwable());
        }

        //Seteamos por defecto el usuario como activo al momento de crearlo.
        user.setActive(true);
        //Creamos el usuario
        User newUser = userRepository.save(user);

        //Una vez creado el usuario, agregamos los teléfonos asociados en la tabla "phone"
        List<Phone> phones = user.getPhones();
        if(phones.size() > 0) {
            for (int i = 0; i < phones.size(); i++) {
                phones.get(i).setUser(newUser);
            }
            phoneService.createPhones(phones);
        }

        return newUser;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User getUser(String email) {
        return userRepository.findByEmail(email);
    }

    @Override
    public boolean deleteUser(User user) {
        try{
            userRepository.delete(user);
            return true;
        }
        catch (Exception ex){
            return false;
        }
    }

    //método que utiliza expresiones regulares para validar que el formato de un email sea correcto.
    public boolean isAValidEmail(String emailAddress) {
        String regexPattern = "^(.+)@(\\S+)$";
        return Pattern.compile(regexPattern)
                .matcher(emailAddress)
                .matches();
    }

    //método que utiliza expresiones regulares para validar que el formato de la contraseña sea correcto.
    public boolean isAValidPassword(String password) {
        String regexPattern = "^(?=.*[A-Z])(?=.*[0-9].*[0-9]).+$";
        return Pattern.compile(regexPattern)
                .matcher(password)
                .matches();
    }
}
