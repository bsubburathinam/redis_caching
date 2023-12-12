package com.example.demo;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.JacksonObjectWriter;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.util.List;
import java.util.function.Supplier;

@Configuration
@AllArgsConstructor
public class CacheConfig {

    private final RedisConnectionFactory connectionFactory;
    private final ObjectMapper objectMapper;


    @Bean
    @Primary
    public RedisCacheManager defaultCacheManager() {
        return buildCacheManager(GenericJackson2JsonRedisSerializer::new);
    }

    @Bean
    public RedisCacheManager professorListCacheManager() {
        return buildCacheManager(() ->
            new GenericJackson2JsonRedisSerializer(
                objectMapper,
                (objectMapper, bytes, type) -> objectMapper.readValue(bytes, new TypeReference<List<Professor>>() {
                }),
                JacksonObjectWriter.create()
            ));
    }

    private <T> RedisCacheManager buildCacheManager(Supplier<GenericJackson2JsonRedisSerializer> serializerSupplieralizer) {
        System.out.println("CacheConfig::cacheManager");
        return
            RedisCacheManager
                .builder(connectionFactory)
                .cacheDefaults(
                    RedisCacheConfiguration.defaultCacheConfig().serializeValuesWith(
                        RedisSerializationContext.SerializationPair.fromSerializer(
                            serializerSupplieralizer.get()
                        )
                    )
                )
                .build();
    }

}
