package com.es.phoneshop.web.filters;

import com.es.phoneshop.model.dos.DosService;
import com.es.phoneshop.model.dos.DosServiceImpl;

import javax.servlet.*;
import java.io.IOException;

public class DosFilter implements Filter {
    private DosService dosService;

    @Override
    public void init(FilterConfig filterConfig) {
        dosService = DosServiceImpl.getInstance();
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        if (dosService.isAllowed(servletRequest.getRemoteAddr())) {
            filterChain.doFilter(servletRequest, servletResponse);
        } else {
            throw new RuntimeException("Dos attack");
        }
    }

    @Override
    public void destroy() {
    }

    void setDosService(DosService dosService) {
        this.dosService = dosService;
    }
}
