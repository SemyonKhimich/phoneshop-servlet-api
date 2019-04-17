package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.dos.DosService;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import javax.servlet.*;
import java.io.IOException;

import static org.mockito.Mockito.*;

@RunWith(MockitoJUnitRunner.class)
public class DosFilterTest {
    private DosFilter filter = new DosFilter();
    @Mock
    private DosService dosService;
    @Mock
    private FilterChain filterChain;
    @Mock
    private ServletRequest request;
    @Mock
    private ServletResponse response;

    private String remoteAddress = "remoteAddress";

    @Before
    public void setup() {
        filter.setDosService(dosService);
        when(request.getRemoteAddr()).thenReturn(remoteAddress);
    }

    @Test
    public void testDoFilterIsAllowedTrue() throws ServletException, IOException {
        when(dosService.isAllowed(remoteAddress)).thenReturn(true);
        filter.doFilter(request, response, filterChain);
        verify(filterChain).doFilter(request, response);
    }
    @Test(expected = RuntimeException.class)
    public void testDoFilterIsAllowedFalse() throws ServletException, IOException{
        when(dosService.isAllowed(remoteAddress)).thenReturn(false);
        filter.doFilter(request, response, filterChain);

    }
}
