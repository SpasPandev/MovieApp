package com.spaspandev.movieapp.config;

import com.spaspandev.movieapp.dto.MovieFullInfoDto;
import com.spaspandev.movieapp.model.entity.Movie;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

@Configuration
public class BeanConfiguration {

    @Bean
    public ModelMapper modelMapper() {

        ModelMapper modelMapper = new ModelMapper();

        modelMapper
                .typeMap(MovieFullInfoDto.class, Movie.class)
                .addMappings(mapper ->
                        mapper.skip(Movie::setId))
                .addMappings(mapper ->
                        mapper.map(MovieFullInfoDto::getId, Movie::setMovie_external_id))
                .addMappings(mapper ->
                        mapper.map(MovieFullInfoDto::getTitle, Movie::setName))
                .addMappings(mapper ->
                        mapper.map(MovieFullInfoDto::getOverview, Movie::setDescription))
        ;

        modelMapper
                .typeMap(Movie.class, MovieFullInfoDto.class)
                .addMappings(mapper ->
                        mapper.map(Movie::getName, MovieFullInfoDto::setTitle))
                .addMappings(mapper ->
                        mapper.map(Movie::getDescription, MovieFullInfoDto::setOverview));

        return modelMapper;
    }

    @Bean
    public RestTemplate restTemplate() {

        return new RestTemplate();
    }
}
