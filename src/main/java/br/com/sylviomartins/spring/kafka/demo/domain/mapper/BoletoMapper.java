package br.com.sylviomartins.spring.kafka.demo.domain.mapper;

import br.com.sylviomartins.spring.kafka.demo.domain.document.Boleto;
import br.com.sylviomartins.spring.kafka.demo.domain.vo.EventoVO;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface BoletoMapper {

    Boleto toDocument(EventoVO source);

    EventoVO toVO(Boleto source);

}
