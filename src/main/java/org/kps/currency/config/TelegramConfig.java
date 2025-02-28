package org.kps.currency.config;

import lombok.Data;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
public class TelegramConfig {

    @Value("${tg.bot_name}")
    private String botName;

    @Value("${tg.bot_token}")
    private String botToken;

}
