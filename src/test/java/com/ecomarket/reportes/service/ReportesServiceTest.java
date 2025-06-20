package com.ecomarket.reportes.service;




import org.junit.jupiter.api.BeforeEach;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.ecomarket.reportes.repository.ReportesRepository;

public class ReportesServiceTest {
    @Mock
    private ReportesRepository reportesRepository;

    @InjectMocks
    private ReportesService reportesService;

    @BeforeEach
    void setUp(){
        MockitoAnnotations.openMocks(this);
    }


}