package org.spgroup;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import(value = { ApplicationConfig.class})
public class ApplicationTestConfig {

}
