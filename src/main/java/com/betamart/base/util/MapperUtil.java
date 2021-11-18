package com.betamart.base.util;

import org.modelmapper.ModelMapper;
import org.modelmapper.spi.MatchingStrategy;

public class MapperUtil {

    public static <T> T parse(Object o, Class<T> clazz, MatchingStrategy strategy){
        ModelMapper mapper = new ModelMapper();
        mapper.getConfiguration().setMatchingStrategy(strategy);
        return mapper.map(o, clazz);
    }
}

