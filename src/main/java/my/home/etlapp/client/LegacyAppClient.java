package my.home.etlapp.client;

import my.home.etlapp.dto.BusinessDto;

public interface LegacyAppClient {

    BusinessDto getValue(long id);
}
