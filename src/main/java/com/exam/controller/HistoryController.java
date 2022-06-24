package com.exam.controller;

import com.exam.model.History;
import com.exam.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/history")
@CrossOrigin("*")
public class HistoryController {

    @Autowired
    HistoryService historyService;

    @PostMapping("/")
    public ResponseEntity<History> addHistory(@RequestBody History history) {
        return ResponseEntity.ok(this.historyService.addHistory(history));
    }

    @GetMapping("/")
    public ResponseEntity<?> getHistory() {
        return ResponseEntity.ok(this.historyService.getHistories());
    }

}
