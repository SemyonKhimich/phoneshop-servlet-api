package com.es.phoneshop.model.dos;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class DosServiceImpl implements DosService {
    private static final DosServiceImpl instance = new DosServiceImpl();
    private Map<String, AtomicInteger> ipCallCount;
    private Map<String, Date> lockedIps;
    private static final int THRESHOLD = 30;
    private static final int LOCK_TIME_MINUTES = 1000;
    private static final int NUMBER_MILLISECONDS_IN_MINUTE = 60000;
    private volatile Date lastResetDate;

    public static DosServiceImpl getInstance() {
        return instance;
    }

    private DosServiceImpl() {
        ipCallCount = new ConcurrentHashMap<>();
        lockedIps = new ConcurrentHashMap<>();
        lastResetDate = new Date();
    }

    @Override
    public boolean isAllowed(String ip) {
        if (isLockIp(ip)) {
            return false;
        }
        mayBeReset();
        AtomicInteger count = ipCallCount.get(ip);
        if (count == null) {
            count = new AtomicInteger(0);
            ipCallCount.put(ip, count);
        }
        int value = count.incrementAndGet();
        if (value > THRESHOLD) {
            lockedIps.put(ip, new Date());
            ipCallCount.remove(ip);
            return false;
        }
        return true;
    }

    private boolean isLockIp(String ip) {
        Date lockDate = lockedIps.get(ip);
        if (lockDate != null) {
            Date currentDate = new Date();
            if (currentDate.getTime() - lockDate.getTime() < LOCK_TIME_MINUTES * NUMBER_MILLISECONDS_IN_MINUTE) {
                return true;
            } else {
                lockedIps.remove(ip);
            }
        }
        return false;
    }

    private void mayBeReset() {
        Date date = new Date();
        if (date.getTime() - lastResetDate.getTime() > NUMBER_MILLISECONDS_IN_MINUTE) {
            lastResetDate = date;
            ipCallCount.clear();
        }
    }

    void setIpCallCount(Map<String, AtomicInteger> ipCallCount) {
        this.ipCallCount = ipCallCount;
    }

    void setLastResetDate(Date lastResetDate) {
        this.lastResetDate = lastResetDate;
    }

    void setLockedIps(Map<String, Date> lockedIps) {
        this.lockedIps = lockedIps;
    }
}
