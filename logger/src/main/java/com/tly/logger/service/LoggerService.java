package com.tly.logger.service;

import java.util.Objects;

public interface LoggerService {

    void infoLogOne();

    void infoLogTwo();

    String loggerFormat(String packageName, Integer ... args);

}
