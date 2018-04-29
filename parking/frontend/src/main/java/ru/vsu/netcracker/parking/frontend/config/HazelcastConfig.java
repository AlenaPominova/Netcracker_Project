package ru.vsu.netcracker.parking.frontend.config;

import com.hazelcast.config.Config;
import com.hazelcast.config.EvictionPolicy;
import com.hazelcast.config.MapConfig;
import com.hazelcast.config.MaxSizeConfig;
import com.hazelcast.core.Hazelcast;
import com.hazelcast.spring.cache.HazelcastCacheManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@EnableCaching
@Configuration
public class HazelcastConfig {

    @Autowired
    CacheManager cacheManager;

    @Bean(name = "cacheManager")
    public CacheManager cacheManager() {
        return new HazelcastCacheManager(Hazelcast.newHazelcastInstance(hazelCastConfig()));
    }

    @Bean
    public Config hazelCastConfig() {
        return new Config()
                .setInstanceName("hazelcast-instance")
                .addMapConfig(
                        new MapConfig()
                                .setName("objects-cache")
                                .setEvictionPolicy(EvictionPolicy.LRU)
                                .setTimeToLiveSeconds(0)
                                .setMaxSizeConfig(new MaxSizeConfig(1000, MaxSizeConfig.MaxSizePolicy.PER_PARTITION))
                                .setStatisticsEnabled(true));
                //.setProperty("hazelcast.logging.type", "log4j2");
    }
}
