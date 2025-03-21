package com.blbulyandavbulyan.elastic.search.controller;

import com.blbulyandavbulyan.elastic.search.model.Event;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;

public record EventQuery(
        @RequestParam(required = false)
        String title,
        @RequestParam(required = false)
        LocalDateTime afterDateTime,
        @RequestParam(required = false)
        Event.Type type) {
}
