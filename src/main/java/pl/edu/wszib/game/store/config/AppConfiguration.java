package pl.edu.wszib.game.store.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import pl.edu.wszib.game.store.filters.AdminFilter;

@Configuration
@ComponentScan("pl.edu.wszib.game.store")
public class AppConfiguration {

    @Bean
    public FilterRegistrationBean<AdminFilter> adminFilter() {
        FilterRegistrationBean<AdminFilter> registrationBean
                = new FilterRegistrationBean<>();

        registrationBean.setFilter(new AdminFilter());
        registrationBean.addUrlPatterns("/game/*");
        registrationBean.setOrder(1);

        return registrationBean;
    }
}
