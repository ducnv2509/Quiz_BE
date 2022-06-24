package com.exam.service.impl;

import com.exam.model.History;
import com.exam.repository.HistoryRepository;
import com.exam.service.HistoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Service
public class HistoryServiceImpl implements HistoryService {

    @Autowired
    HistoryRepository historyRepository;

    @Override
    public History addHistory(History history) {
        history.setCreateDate(new Date());
        return this.historyRepository.save(history);
    }

    @Override
    public Set<History> getHistories() {
        return new HashSet<>(this.historyRepository.findAll());
    }
}
