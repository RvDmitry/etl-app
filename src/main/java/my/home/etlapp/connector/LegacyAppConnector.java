package my.home.etlapp.connector;

import my.home.etlapp.dto.BusinessDto;
import org.springframework.web.bind.annotation.PathVariable;

public interface LegacyAppConnector {

    BusinessDto getValue(@PathVariable("id") Long id);
}
