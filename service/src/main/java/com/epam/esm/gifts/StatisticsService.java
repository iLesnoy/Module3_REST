package com.epam.esm.gifts;

import com.epam.esm.gifts.dto.TagDto;
import com.epam.esm.gifts.model.Tag;

import java.util.Optional;

public interface StatisticsService {

    TagDto mostWidelyUsedTag();
}
