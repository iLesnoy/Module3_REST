package com.epam.esm.gifts.dao.mapper;

import com.epam.esm.gifts.model.GiftCertificate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class GiftCertificateRowMapper implements RowMapper<GiftCertificate> {
    private static final DateTimeFormatter FORMATTER = DateTimeFormatter.ofPattern("yyyy-MM-dd kk:mm:ss");

    @Override
    public GiftCertificate mapRow(ResultSet rs, int rowNum) throws SQLException {
        GiftCertificate certificate = new GiftCertificate();
        certificate.setId(rs.getLong("id"));
        certificate.setName(rs.getString("name"));
        certificate.setDescription(rs.getString("description"));
        certificate.setPrice(rs.getBigDecimal("price"));
        certificate.setDuration(rs.getInt("duration"));
        certificate.setCreateDate(LocalDateTime.parse(rs.getString("create_date"), FORMATTER));
        certificate.setLastUpdateDate(LocalDateTime.parse(rs.getString("last_update_date"), FORMATTER));
        return certificate;
    }
}