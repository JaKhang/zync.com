package com.zync.network.account.application.queries;

import com.zync.network.core.domain.ZID;import com.zync.network.account.application.model.DevicePayload;
import com.zync.network.core.mediator.Handler;
import com.zync.network.core.mediator.RequestHandler;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.List;

@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@AllArgsConstructor
@Handler
public class SignedDevicesQueryHandler implements RequestHandler<SignedDevicesQuery, List<DevicePayload>> {
    JdbcTemplate jdbcTemplate;

    @Override
    public List<DevicePayload> handle(SignedDevicesQuery request) {
        String sql = "Select id, name, os, browse,trusted, login_at from devices where account_id = ?";
        return jdbcTemplate.query(sql, new DevicePayloadMapper(), request.accountId().toString());
    }

    private static class DevicePayloadMapper implements RowMapper<DevicePayload> {

        @Override
        public DevicePayload mapRow(ResultSet rs, int rowNum) throws SQLException {
            return new DevicePayload(
                    ZID.from(rs.getString("id")),
                    rs.getString("name"),
                    rs.getString("os"),
                    rs.getString("browser"),
                    rs.getTimestamp("login_at"),
                    rs.getBoolean("trusted")
            );
        }
    }
}
