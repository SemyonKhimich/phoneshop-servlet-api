package com.es.phoneshop.model;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;

import java.util.Date;
import java.util.Map;
import java.util.concurrent.atomic.AtomicInteger;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DosServiceImplTest {
    private static final int THRESHOLD = 30;
    private DosServiceImpl dosService = DosServiceImpl.getInstance();
    @Mock
    private Map<String, Date> lockedIps;
    private static final String IP = "ip";
    @Mock
    private Date date;
    @Mock
    private Map<String, AtomicInteger> ipCallCount;
    @Mock
    private Date lastResetDate;


    @Before
    public void setup() {
        when(lockedIps.get(IP)).thenReturn(date);
        when(date.getTime()).thenReturn(0L);
        dosService.setLockedIps(lockedIps);
        dosService.setIpCallCount(ipCallCount);
        dosService.setLastResetDate(lastResetDate);
        when(lastResetDate.getTime()).thenReturn(0L);
    }

    @Test
    public void testResetIpCallCount() {
        dosService.isAllowed(IP);
        verify(ipCallCount).clear();
    }

    @Test
    public void testIsLookIpTrue() {
        Date currentDate = new Date();
        when(date.getTime()).thenReturn(currentDate.getTime());
        assertFalse(dosService.isAllowed(IP));
    }

    @Test
    public void testRemoveFromLockedIP() {
        dosService.isAllowed(IP);
        verify(lockedIps).remove(IP);
    }

    @Test
    public void testPutToLockIps() {
        AtomicInteger atomicInteger = new AtomicInteger(THRESHOLD);
        when(ipCallCount.get(IP)).thenReturn(atomicInteger);
        dosService.isAllowed(IP);
        verify(ipCallCount).remove(IP);
        verify(lockedIps).put(eq(IP), any(Date.class));
    }
}
