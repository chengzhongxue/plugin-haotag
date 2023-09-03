package run.halo.haotag;

import org.pf4j.PluginWrapper;
import org.springframework.stereotype.Component;
import run.halo.app.plugin.BasePlugin;

/**
 * @author chengzhongxue
 * @since 2.0.0
 */
@Component
public class HaoTagPlugin extends BasePlugin {


    public HaoTagPlugin(PluginWrapper wrapper) {
        super(wrapper);
    }

    @Override
    public void start() {
    }

    @Override
    public void stop() {
    }

}
