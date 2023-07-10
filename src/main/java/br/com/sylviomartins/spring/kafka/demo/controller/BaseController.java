package br.com.sylviomartins.spring.kafka.demo.controller;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;

@NoArgsConstructor(access = AccessLevel.PACKAGE)
class BaseController {

    protected static final String ID_PATH_VARIABLE = "/{id:^(?!count|all).+}"; // NOSONAR

}
