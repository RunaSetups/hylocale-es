package com.runasetups.hylocale.config;

import com.hypixel.hytale.codec.Codec;
import com.hypixel.hytale.codec.KeyedCodec;
import com.hypixel.hytale.codec.builder.BuilderCodec;

public class HyLocaleConfig {

    public static final BuilderCodec<HyLocaleConfig> CODEC = BuilderCodec.builder(HyLocaleConfig.class, HyLocaleConfig::new)
            .append(
                    new KeyedCodec<>("DefaultLanguage", Codec.STRING),
                    (config, value, extraInfo) -> config.defaultLanguage = value,
                    (config, extraInfo) -> config.defaultLanguage
            )
            .add()
            .build();

    private String defaultLanguage = "es-ES";

    private HyLocaleConfig() {
    }

    public String getDefaultLanguage() {
        return defaultLanguage;
    }
}
