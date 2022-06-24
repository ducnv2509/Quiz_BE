package com.exam.service;

import com.exam.model.History;

import java.util.Set;

public interface HistoryService {
    History addHistory(History history);

    Set<History> getHistories();
}
