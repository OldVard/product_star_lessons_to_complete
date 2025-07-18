package org.example.config;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

// Объявляем конфигурацию приложения
// ComponentScan - сканирует пакет org.example и создает бины для всех классов внутри пакета
@Configuration
@ComponentScan("org.example")
public class ApplicationConfig { }
