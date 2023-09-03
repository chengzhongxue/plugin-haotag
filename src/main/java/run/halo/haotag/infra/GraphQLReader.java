package run.halo.haotag.infra;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;

import java.util.Map;
import java.util.function.BiFunction;

/**
 * @author chengzhongxue
 * @date 2023/9/3
 * @since 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class GraphQLReader {

    private final ReactiveSettingFetcher reactiveSettingFetcher;

    @NotNull
    public Mono<String> executeQuery(String owner, String repo, BiFunction<String, String, String> queryBuilder) {
        return this.reactiveSettingFetcher.get("basic")
                .map(setting -> setting.get("githubToken").asText("Github Token"))
                .flatMap(token -> WebClient.builder()
                        .baseUrl("https://api.github.com/graphql")
                        .defaultHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                        .defaultHeader(HttpHeaders.AUTHORIZATION, "token " + token)
                        .build()
                        .post()
                        .bodyValue(Map.of(
                                "query", queryBuilder.apply(owner, repo)
                        ))
                        .retrieve()
                        .bodyToMono(String.class)
                );
    }

}
