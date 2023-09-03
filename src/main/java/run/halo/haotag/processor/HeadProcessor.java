package run.halo.haotag.processor;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;
import org.thymeleaf.context.ITemplateContext;
import org.thymeleaf.model.IModel;
import org.thymeleaf.model.IModelFactory;
import org.thymeleaf.processor.element.IElementModelStructureHandler;
import reactor.core.publisher.Mono;
import run.halo.app.plugin.SettingFetcher;
import run.halo.app.theme.dialect.TemplateHeadProcessor;
import run.halo.haotag.entity.SettingsReader;
import run.halo.haotag.util.DomBuilder;
import run.halo.haotag.util.InferStream;

@Component
@AllArgsConstructor
public class HeadProcessor implements TemplateHeadProcessor {

    private final SettingFetcher settingFetcher;
    @Override
    public Mono<Void> process(ITemplateContext context, IModel model,
                              IElementModelStructureHandler structureHandler) {
        final IModelFactory modelFactory = context.getModelFactory();
        // 全响应式链路
        return InferStream
                // 独立或文章页 JS
                .<Void>infer(true)
                .success(() -> settingFetcher
                        .fetch("basic", SettingsReader.class)
                        .map(config -> {
                            model.add(modelFactory.createText(
                                    DomBuilder.use()
                                            .style("/css/tool-bench.css")
                                            .script("/lib/CustomDom.js")
                                            .themeScript("/themes/theme-hao/assets/js/custom.js")
                                            .build()));
                            return Mono.empty();
                        })
                        .orElse(Mono.empty()).then()).last();
    }

}
