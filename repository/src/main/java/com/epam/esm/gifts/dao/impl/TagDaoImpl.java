package com.epam.esm.gifts.dao.impl;

import com.epam.esm.gifts.dao.TagDao;
import com.epam.esm.gifts.model.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.sql.PreparedStatement;
import java.sql.Statement;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static com.epam.esm.gifts.dao.constants.SqlQuery.*;

@Component
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> findAll() {
        return jdbcTemplate.query(FIND_ALL_TAGS,new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement(INSERT_TAG, Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        tag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Optional<Tag> optionalTag = jdbcTemplate.query(FIND_TAG_BY_ID,new Object[]{id},new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny();
        return optionalTag.isPresent() ? optionalTag: Optional.empty();
    }

    @Override
    public int update(long id, Tag updatedTag) {
        return jdbcTemplate.update(UPDATE_TAG_BY_ID,updatedTag.getName(),id);
    }

    @Override
    public int deleteById(Long id) {
        return jdbcTemplate.update(DELETE_TAG_BY_ID,id);
    }

    @Override
    public Tag findOrCreateTag(Tag tag) {
        return jdbcTemplate.query(FIND_TAG_BY_NAME,new Object[]{tag.getName()},new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny().orElseGet(() -> create(tag));
    }

    @Override
    public boolean isUsed(Long id) {
        return jdbcTemplate.queryForObject(COUNT_TAG_IN_USE, Integer.class, id) > 0;
    }
}
