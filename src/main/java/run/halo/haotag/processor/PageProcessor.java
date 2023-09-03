package run.halo.haotag.processor;

import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.theme.ReactiveSinglePageContentHandler;
import run.halo.haotag.infra.ProcessorHandler;

/**
 * @author chengzhongxue
 * @date 2023/9/3
 * @since 1.0
 */
@Component
public class PageProcessor implements ReactiveSinglePageContentHandler {

    @Resource
    private ProcessorHandler processorHandler;

    @Override
    public Mono<SinglePageContentContext> handle(@NotNull final SinglePageContentContext singlePageContent) {
        return processorHandler.handlePageProcessor(singlePageContent);
    }

}
