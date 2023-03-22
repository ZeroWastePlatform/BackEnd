package com.greenUs.server.reviewAndAsk.service;

import com.greenUs.server.reviewAndAsk.domain.Ask;
import com.greenUs.server.reviewAndAsk.dto.response.AskResponse;
import com.greenUs.server.reviewAndAsk.repository.AskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AskService {

    private static final int MAX_ASKS_COUNT = 5;

    private final AskRepository askRepository;

    public Page<AskResponse> getAskByProductId(Long id, int page) {
        PageRequest pageRequest = PageRequest.of(page, MAX_ASKS_COUNT, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Ask> asks = askRepository.findByProductId(id, pageRequest);
        return transformAsks(asks);
    }

    private Page<AskResponse> transformAsks(Page<Ask> asks) {
        return asks.map(ask ->
                AskResponse
                        .builder()
                        .id(ask.getId())
                        .title(ask.getTitle())
                        .content(ask.getContent())
                        .secret(ask.getSecret())
                        .answer(ask.getAnswer())
                        .build());
    }
}
