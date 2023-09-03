package run.halo.haotag.processor;

import jakarta.annotation.Resource;
import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import run.halo.app.theme.ReactivePostContentHandler;
import run.halo.haotag.infra.ProcessorHandler;

/**
 * @author chengzhongxue
 * @date 2023/9/3
 * @since 1.0
 */
@Component
public class PostProcessor implements ReactivePostContentHandler {

    @Resource
    private ProcessorHandler processorHandler;

    @Override
    public Mono<PostContentContext> handle(@NotNull final PostContentContext postContent) {
        return processorHandler.handlePostProcessor(postContent);
    }

}
