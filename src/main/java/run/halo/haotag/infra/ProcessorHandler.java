package run.halo.haotag.infra;

import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.ReactiveSettingFetcher;
import run.halo.app.theme.ReactivePostContentHandler;
import run.halo.app.theme.ReactiveSinglePageContentHandler;
import run.halo.haotag.entity.SettingsReader;
import run.halo.haotag.util.PostUtil;

import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * @author chengzhongxue
 * @date 2023/9/3
 * @since 1.0
 */
@Slf4j
@Component
@AllArgsConstructor
public class ProcessorHandler {

    private final ReactiveSettingFetcher reactiveSettingFetcher;

    @NotNull
    public Mono<ReactivePostContentHandler.PostContentContext> handlePostProcessor(
            @NotNull ReactivePostContentHandler.PostContentContext postContentContext) {
        return this.doProcess(postContentContext,
                ReactivePostContentHandler.PostContentContext::getContent,
                ReactivePostContentHandler.PostContentContext::getRaw,
                postContentContext::setContent,
                postContentContext.getPost().getSpec().getHtmlMetas()::add);
    }

    @NotNull
    public Mono<ReactiveSinglePageContentHandler.SinglePageContentContext> handlePageProcessor(
            @NotNull ReactiveSinglePageContentHandler.SinglePageContentContext singlePageContentContext) {
        return this.doProcess(singlePageContentContext,
                ReactiveSinglePageContentHandler.SinglePageContentContext::getContent,
                ReactiveSinglePageContentHandler.SinglePageContentContext::getRaw,
                singlePageContentContext::setContent,
                singlePageContentContext.getSinglePage().getSpec().getHtmlMetas()::add);
    }

    @NotNull
    private <T> Mono<T> doProcess(@NotNull T contentContext,
                                  @NotNull Function<T, String> getContent,
                                  @NotNull Function<T, String> getRaw,
                                  @NotNull Consumer<String> setContent,
                                  @NotNull Consumer<Map<String, String>> addHtmlMetas) {
        return reactiveSettingFetcher
                .fetch("basic", SettingsReader.class)
                .flatMap(config -> Mono.just(contentContext)
                        .flatMap(cc -> {
                            String raw = getRaw.apply(cc);
                            String content = getContent.apply(cc);
                            Map<String, String> htmlMetas = new HashMap<>();
                            htmlMetas.put("count-words", String.valueOf(PostUtil.countWords(content)));
                            htmlMetas.put("count-images", String.valueOf(PostUtil.countImages(content)));
                            addHtmlMetas.accept(htmlMetas);
                            Set<String> prefixes = new HashSet<>();
                            prefixes.add("hao");
                            String updatedContent = PostUtil.fixMarkdownAndElementTag(raw, prefixes);
                            setContent.accept(updatedContent);
                            return Mono.just(cc);
                        }));
    }

}
