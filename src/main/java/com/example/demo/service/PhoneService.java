package com.example.demo.service;

import com.example.demo.model.Phone;
import com.example.demo.repository.IPhoneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
@AllArgsConstructor
public class PhoneService implements IPhoneService{
    private final IPhoneRepository phoneRepository;

    @Override
    public List<Phone> createPhones(List<Phone> phones) {

        return phoneRepository.saveAll(phones);
    }
}
