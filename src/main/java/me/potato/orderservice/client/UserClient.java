package me.potato.orderservice.client;

import me.potato.orderservice.dto.TransactionRequestDto;
import me.potato.orderservice.dto.TransactionResponseDto;
import me.potato.orderservice.dto.UserDto;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class UserClient {
    private final WebClient client;

    public UserClient(@Value("${user.service.url}") String url) {
        this.client = WebClient.create(url);
    }

    public Mono<UserDto> getUserById(final Long userId) {
        return this.client.get()
                .uri("/{id}", userId)
                .retrieve()
                .bodyToMono(UserDto.class);
    }

    public Mono<TransactionResponseDto> authorizeTransaction(final TransactionRequestDto requestDto) {
        return this.client.post()
                .uri("/transaction")
                .bodyValue(requestDto)
                .retrieve()
                .bodyToMono(TransactionResponseDto.class);

    }

    public Flux<UserDto> getAllUsers() {
        return this.client.get()
                .retrieve()
                .bodyToFlux(UserDto.class);
    }
}
