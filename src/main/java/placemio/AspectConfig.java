package placemio;


import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

@Configuration
@ComponentScan("placemio.services")
@EnableAspectJAutoProxy
public class AspectConfig {
}
