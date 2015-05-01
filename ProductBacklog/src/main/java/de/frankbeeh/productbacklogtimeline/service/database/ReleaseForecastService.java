package de.frankbeeh.productbacklogtimeline.service.database;

import java.sql.Connection;
import java.time.LocalDateTime;
import java.util.List;

import de.frankbeeh.productbacklogtimeline.domain.ReleaseForecast;
import de.frankbeeh.productbacklogtimeline.service.database.mapper.ReleaseForecastMapper;

public class ReleaseForecastService {
    private ReleaseForecastMapper releaseForecastMapper;

    public ReleaseForecastService(Connection connection) {
        releaseForecastMapper = new ReleaseForecastMapper(connection);
    }

    public void insert(ReleaseForecast releaseForecast) {
        releaseForecastMapper.insert(releaseForecast);
    }

    public ReleaseForecast get(LocalDateTime localDateTime) {
        return releaseForecastMapper.get(localDateTime);
    }

    public List<LocalDateTime> getAllIds() {
        return releaseForecastMapper.getAllIds();
    }
}
