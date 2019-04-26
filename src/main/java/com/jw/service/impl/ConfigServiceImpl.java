package com.jw.service.impl;

import com.jw.entity.ConfigEntity;
import com.jw.repository.ConfigRepository;
import com.jw.service.ConfigService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author 姜雯
 * @date 2019/4/25--22:42
 */
@Service
public class ConfigServiceImpl implements ConfigService {

    @Autowired
    private ConfigRepository configRepository;
    @Override
    public List<ConfigEntity> getAll() {
        return configRepository.findAll();
    }
}
