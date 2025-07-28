package br.com.fiap.lanchonete.business.core.usecase.impl;

import org.junit.platform.suite.api.SelectClasses;
import org.junit.platform.suite.api.Suite;

@Suite
@SelectClasses({
    ClienteUseCasesImplTest.class,
    OrdemServicoUseCasesImplTest.class,
    PedidoUseCasesImplTest.class
})
public class UseCaseTestSuite {
    // Test suite to run all use case tests together
}