package com.maybank.platform.services.restapi.task;

import com.maybank.platform.services.restapi.model.FileInfo;
import com.maybank.platform.services.restapi.services.FileInfoService;
import com.maybank.platform.services.restapi.services.TransactionService;
import com.maybank.platform.services.util.RedisUtil;
import com.maybank.platform.services.util.constants.GlobalConstants;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ProcessScheduler {
    private final FileInfoService fileInfoService;
    private final TransactionService transactionService;
    private final RedisUtil redisUtil;

    @Value("${csv.destination.directory:}")
    private String fileDestinationPath = "";

    @Scheduled(fixedRate = 60000) // Scan every 60 seconds
    public void processTxtData() {
        List<FileInfo> fileInfoList = fileInfoService.getPendingFiles(1);
        fileInfoList.forEach(fileInfo -> {
            String lockKey = GlobalConstants.FILE_INFO_LOCK_KEY + fileInfo.getId();
            if (!redisUtil.isLocked(lockKey)) {
                Boolean isLocked = redisUtil.setLock(lockKey, 3600);
                if (Boolean.TRUE.equals(isLocked)) {
                    try {
                        fileInfo.setVersion(2);
                        fileInfoService.updateVersionFile(fileInfo.getId());
                        String filePath = fileDestinationPath + fileInfo.getFileName();
                        transactionService.transactionSave(filePath, fileInfo);
                    } finally {
                        redisUtil.releaseLock(lockKey);
                    }
                }
            }
        });
    }
}
