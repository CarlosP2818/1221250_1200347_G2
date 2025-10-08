package pt.psoft.g1.psoftg1.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializationContext;

//@Configuration
//@EnableCaching
public class RedisCacheConfig {
// Login not working because of caching
//    @Bean
//    public RedisCacheConfiguration cacheConfiguration() {
//        ObjectMapper mapper = new ObjectMapper();
//        mapper.registerModule(new JavaTimeModule()); // Suporte para LocalDateTime
//        mapper.findAndRegisterModules(); // detecta outros módulos
//
//        GenericJackson2JsonRedisSerializer serializer = new GenericJackson2JsonRedisSerializer(mapper);
//
//        return RedisCacheConfiguration.defaultCacheConfig()
//                .serializeValuesWith(
//                        RedisSerializationContext.SerializationPair.fromSerializer(serializer)
//                );
//    }
}
