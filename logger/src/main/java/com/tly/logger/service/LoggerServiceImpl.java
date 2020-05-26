package com.tly.logger.service;

import com.tly.logger.LogFormat;
import com.tly.logger.TlyLogCenter;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.lang.invoke.MethodHandles;
import java.util.Objects;

@Service
@TlyLogCenter(logTag = "aaa")
public class LoggerServiceImpl implements LoggerService {

    private Logger logger = LoggerFactory.getLogger(MethodHandles.lookup().lookupClass());

    @Autowired
    private LoggerService loggerService;

    @Override
    @TlyLogCenter(bizID = "biz-1")
    public void infoLogOne() {
        logger.info("方法[infoLogOne]开始打印日志...");
        loggerService.infoLogTwo();
        logger.info("方法[infoLogOne]结束打印日志...");
    }

    @Override
    @TlyLogCenter(bizID = "biz-2")
    public void infoLogTwo() {
        logger.info("方法[infoLogTwo]开始打印日志...");
        try {
            Thread.sleep(2000L);
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("方法[infoLogTwo]结束打印日志...");
        logger.info("asd....", LogFormat.builder().logTag("get.user").build());
    }

    @Override
    public String loggerFormat(String packageName, Integer... args) {
        // 若 length没有传或者包名只有一个
        if (args.length == 0 || !packageName.contains(".")) {
            return packageName;
        }
        int length = args[0];
        String[] packages = packageName.split("\\.");
        int packageLen = packages.length;
        // length为 0或者包名只有一个
        if (length == 0 || packageLen == 1) {
            return packages[packageLen - 1];
        }
        String result = packages[packageLen - 1];
        // 若最右边一个包名的长度大于 length
        if (result.length() >= length) {
            return result;
        }
        // 按照各个包名的首字母 + "#" + result进行拼接
        for (int i = packageLen - 2; i >= 0; i--) {
            result = packages[i].substring(0, 1) + "#" + result;
        }
        // 尝试到第几个右边的包名
        int right = 1;
        // 判断的条件应该取值包全类名的长度
        while (result.length() < packageName.length()) {
            // 获取当前要处理的包名
            String packageStr = packages[packageLen - 1 - right];
            // 尝试进行替换为完整的包名
            int index = result.lastIndexOf("#");
            String var1 = result.substring(0, index);
            String var2 = result.substring(0, var1.length() - 1) + packageStr + "." + result.substring(index + 1);
            // 判断替换后的长度与传入的参数
            if (var2.length() > length) {
                break;
            }
            result = var2;
            right++;
        }
        result = result.replaceAll("#", ".");
        // 若超出，则空格填充
        int sub = length - result.length();
        for (int j = 0; j < sub; j++) {
            result = " " + result;
        }
        return result;
    }
}
