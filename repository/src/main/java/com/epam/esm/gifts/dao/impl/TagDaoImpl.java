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

@Component
public class TagDaoImpl implements TagDao {

    private final JdbcTemplate jdbcTemplate;

    @Autowired
    public TagDaoImpl(JdbcTemplate jdbcTemplate) {
        this.jdbcTemplate = jdbcTemplate;
    }

    @Override
    public List<Tag> show() {
        return jdbcTemplate.query("SELECT * FROM tag",new BeanPropertyRowMapper<>(Tag.class));
    }

    @Override
    public Tag create(Tag tag) {
        KeyHolder keyHolder = new GeneratedKeyHolder();
        jdbcTemplate.update(con -> {
            PreparedStatement ps = con.prepareStatement("INSERT INTO tag (name) VALUES (?)", Statement.RETURN_GENERATED_KEYS);
            ps.setString(1, tag.getName());
            return ps;
        }, keyHolder);
        tag.setId(Objects.requireNonNull(keyHolder.getKey()).longValue());
        return tag;
    }

    @Override
    public Optional<Tag> findById(Long id) {
        Optional<Tag> optionalTag = jdbcTemplate.query("SELECT * FROM tag WHERE id=?",new Object[]{id},new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny();
        if(optionalTag.isPresent()){
            return optionalTag;
        } else {
            return Optional.empty();
        }
    }

    @Override
    public void update(long id,Tag updatedTag) {
        jdbcTemplate.update("UPDATE tag SET name =? WHERE id=?",updatedTag.getName(),id);
    }

    @Override
    public void deleteById(Long id) {
        jdbcTemplate.update("DELETE FROM tag WHERE id=?",id);
    }

    @Override
    public Tag findOrCreateTag(Tag tag) {
        return jdbcTemplate.query("SELECT * FROM tag WHERE name=?",new Object[]{tag.getName()},new BeanPropertyRowMapper<>(Tag.class))
                .stream().findAny().orElseGet(() -> create(tag));
    }
}
