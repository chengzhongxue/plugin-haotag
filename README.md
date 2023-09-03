# Hao-Tag
感谢您下载并使用 Hao Tag 插件，本插件为 Halo 2.x 博客集成 hao主题标签
本插件是基于Tool-Bench魔改而来，感谢Tool-Bench插件：https://github.com/DioxideCN/Tool-Bench

## 开发环境
```shell
git clone git@github.com:chengzhongxue/plugin-haotag.git
# 或者当你 fork 之后
git clone git@github.com:{your_github_id}/plugin-haotag.git
```

修改 Halo 程序的环境配置文件：

```yaml
halo:
  plugin:
    runtime-mode: development
    classes-directories:
      - "build/classes"
      - "build/resources"
    lib-directories:
      - "libs"
    fixedPluginPath:
      - "/path/to/plugin-haotag"
```

## 提供issue
提供 issue 时请附带完整的报错信息，并尽可能多地提供一个可复现该错误的环境信息（如：主题、JDK版本、时间等）。

## 版本支持
1. 1.0.0 及以下的版本支持 Halo 2.8.0+
