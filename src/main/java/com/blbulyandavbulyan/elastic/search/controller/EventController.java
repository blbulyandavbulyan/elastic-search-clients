package com.blbulyandavbulyan.elastic.search.controller;

import com.blbulyandavbulyan.elastic.search.model.Event;
import com.blbulyandavbulyan.elastic.search.service.EventService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/events")
public class EventController {
    private final EventService eventService;

    @GetMapping
    public List<Event> searchEvents(EventQuery eventQuery) {
        return eventService.searchEvents(eventQuery);
    }

    @PostMapping
    public Event save(Event event) {
        eventService.save(event);
        return event;
    }
}
