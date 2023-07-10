package br.com.sylviomartins.spring.kafka.demo.domain.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Boleto {

    private String id;

    private String name;

}
